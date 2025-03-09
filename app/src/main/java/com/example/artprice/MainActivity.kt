package com.example.artprice

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.artprice.Result
import com.example.artprice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        const val EXTRA_PRICE_PIECE = "PRICE_PIECE"
        const val EXTRA_ACCESSORY = "ACCESSORY"
        const val EXTRA_SPINNER_RESULT = "SPINNER_RESULT"
        const val EXTRA_PROFIT = "PROFIT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val spinnerPesoResina = binding.spiResina
        val spinnerProfit = binding.spiTipo

        //Criando Adapter para o SpinnerPesoResina
        val pesoResinaArray = resources.getStringArray(R.array.peso_resina)
        val adapterPesoResina = ArrayAdapter(this, android.R.layout.simple_spinner_item, pesoResinaArray)
        adapterPesoResina.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPesoResina.adapter = adapterPesoResina

        //Criando Adapter para o SpinnerProfit
        val option = resources.getStringArray(R.array.opcao_calculo)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, option)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerProfit.adapter = adapter

        binding.btnCalcular.setOnClickListener {
            val resina = binding.spiResina.toString()
            val value = binding.editValor.text.toString()
            val pieceWeight = binding.editPeso.text.toString()
            val profit = binding.editLucro.text.toString()

            if (resina.isEmpty() || value.isEmpty() || pieceWeight.isEmpty() || profit.isEmpty()) {
                Toast.makeText(this, resources.getString(R.string.erro1), Toast.LENGTH_SHORT).show()
            } else if (value.toDoubleOrNull() == null || pieceWeight.toDoubleOrNull() == null || profit.toDoubleOrNull() == null) {
                Toast.makeText(this, resources.getString(R.string.erro2), Toast.LENGTH_SHORT).show()
            } else {
                calculationPrice()
            }

        }


    }

    private fun calculationPrice(){
        val valueId = binding.editValor
        val weightId = binding.editPeso
        val accessoryId = binding.editAcessorio
        val optionSelect = binding.spiTipo.selectedItem.toString()
        val spiPesoResina = binding.spiResina.selectedItem.toString()
        val profitId = binding.editLucro
        val feesId = binding.editTaxa

        val pesoResina = when {
            spiPesoResina.contains("kg") -> spiPesoResina.replace("kg", "").trim().replace(",", ".").toDoubleOrNull()?.times(1000)
            spiPesoResina.contains("g") -> spiPesoResina.replace("g", "").trim().toDoubleOrNull()
            else -> 0.0
        } ?: 0.0


        val resinaID = binding.spiResina.selectedItem.toString().toDoubleOrNull()
        val value = valueId.text.toString().toDoubleOrNull() ?: 1.0
        val weight = weightId.text.toString().toDoubleOrNull() ?: 0.0
        val accessory = accessoryId.text.toString().toDoubleOrNull() ?: 0.0
        val profit = profitId.text.toString().toDoubleOrNull() ?: 0.0
        val fees = feesId.text.toString().toDoubleOrNull() ?: 0.0

        val pricePiece = (value / pesoResina) * weight
        val costPiece = pricePiece + accessory

        val spinnerResult = when(optionSelect) {
            "Multiplicação" -> costPiece * profit
            "Porcentagem" -> costPiece *(1 + profit / 100)
            else -> 0
        }

        val intent = Intent(this, Result::class.java).apply {
            putExtra(EXTRA_PRICE_PIECE, pricePiece)
            putExtra(EXTRA_ACCESSORY, accessory)
            putExtra(EXTRA_SPINNER_RESULT, spinnerResult)
            putExtra(EXTRA_PROFIT, profit)
        }
        startActivity(intent)
    }

}