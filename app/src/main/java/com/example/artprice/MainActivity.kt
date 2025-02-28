package com.example.artprice

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.artprice.databinding.ActivityMainBinding

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
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
        val spinner = binding.spiTipo

        //Criando Adapter para o Spinner
        val option = resources.getStringArray(R.array.opcao_calculo)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, option)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        binding.btnCalcular.setOnClickListener {
            val resina = binding.editResina.text.toString()
            val value = binding.editValor.text.toString()
            val pieceWeight = binding.editPeso.text.toString()
            val profit = binding.editLucro.text.toString()

            if(resina.isEmpty() && value.isEmpty()){
                Toast.makeText(this, "Digite o peso total da resina e o valor", Toast.LENGTH_SHORT).show()
            } else if (pieceWeight.isEmpty() && profit.isEmpty()){
                Toast.makeText(this, "Digite o peso da peça, o tipo de lucro e o lucro", Toast.LENGTH_SHORT).show()
            } else {
                calculationPrice()
            }

        }


    }

    private fun calculationPrice(){
        val resinaId = binding.editResina
        val valueId = binding.editValor
        val weightId = binding.editPeso
        val accessoryId = binding.editAcessorio
        val optionSelect = binding.spiTipo.selectedItem.toString()
        val profitId = binding.editLucro
        val feesId = binding.editTaxa

        val resina = resinaId.text.toString().toDoubleOrNull() ?: 0.0
        val value = valueId.text.toString().toDoubleOrNull() ?: 1.0
        val weight = weightId.text.toString().toDoubleOrNull() ?: 0.0
        val accessory = accessoryId.text.toString().toDoubleOrNull() ?: 0.0
        val profit = profitId.text.toString().toDoubleOrNull() ?: 0.0
        val fees = feesId.text.toString().toDoubleOrNull() ?: 0.0

        val pricePiece = (value / resina) * weight
        val costPiece = pricePiece + accessory

        val spinnerResult = when(optionSelect) {
            "Multiplicação" -> costPiece * profit
            "Porcentagem" -> costPiece *(1 + profit / 100)
            else -> 0
        }

        binding.txtResultado.text = "O valor da peça é de: R$ %.2f".format(spinnerResult)
    }

}