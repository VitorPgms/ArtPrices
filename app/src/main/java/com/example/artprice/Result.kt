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

        val pricePiece = intent.getDoubleExtra("PRICE_PIECE", 0.0)
        val accessory = intent.getDoubleExtra("ACCESSORY", 0.0)
        val spinnerResultValue = intent.getDoubleExtra("SPINNER_RESULT", 0.0)
        val profit = intent.getDoubleExtra("PROFIT", 0.0)

        pricePieceResult.text = "R$$pricePiece%.2f"
        accessoryResult.text = "R$$accessory"
        profitResult.text = "R$$profit"
        resultResult.text = "R$$spinnerResultValue"


    }
}