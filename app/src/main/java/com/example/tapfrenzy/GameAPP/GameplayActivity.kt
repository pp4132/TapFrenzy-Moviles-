package com.example.tapfrenzy.GameAPP

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tapfrenzy.R

class GameplayActivity : AppCompatActivity() {

    lateinit var tvPuntuacion: TextView
    lateinit var tvNivel: TextView
    lateinit var btnBack: ImageButton
    lateinit var agujeros: List<ImageButton>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_gameplay)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tvPuntuacion = findViewById(R.id.tvPuntuacion)
        tvNivel = findViewById(R.id.tvNivel)
        btnBack = findViewById(R.id.btnBack)

        agujeros = listOf(
            findViewById(R.id.btnAgujero1),
            findViewById(R.id.btnAgujero2),
            findViewById(R.id.btnAgujero3),
            findViewById(R.id.btnAgujero4),
            findViewById(R.id.btnAgujero5),
            findViewById(R.id.btnAgujero6),
            findViewById(R.id.btnAgujero7),
            findViewById(R.id.btnAgujero8),
            findViewById(R.id.btnAgujero9)
        )

        btnBack.setOnClickListener { finish() }

    }
}