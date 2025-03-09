package com.example.artprice

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.artprice.databinding.ActivityResultBinding

class Result : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val pricePieceResult = binding.txtValuePiece
        val accessoryResult = binding.txtValorAcessorio
        val profitResult = binding.txtValorMaodeObra
        val resultResult = binding.txtValorTotalVenda

        val pricePiece = intent.getDoubleExtra(MainActivity.EXTRA_PRICE_PIECE, 0.0)
        val accessory = intent.getDoubleExtra(MainActivity.EXTRA_ACCESSORY, 0.0)
        val spinnerResultValue = intent.getDoubleExtra(MainActivity.EXTRA_SPINNER_RESULT, 0.0)
        val profit = intent.getDoubleExtra(MainActivity.EXTRA_PROFIT, 0.0)

        pricePieceResult.text = String.format("R$ %.2f", pricePiece)
        accessoryResult.text = String.format("R$ %.2f", accessory)
        profitResult.text = "$profit"
        resultResult.text = String.format("R$ %.2f", spinnerResultValue)

    }
}