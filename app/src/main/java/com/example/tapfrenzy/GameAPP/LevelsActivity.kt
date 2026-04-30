package com.example.tapfrenzy.GameAPP

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tapfrenzy.R

class LevelsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_levels)

        val btnN1 = findViewById<Button>(R.id.btnNivel1)
        val btnN2 = findViewById<Button>(R.id.btnNivel2)
        val btnN3 = findViewById<Button>(R.id.btnNivel3)
        val btnN4 = findViewById<Button>(R.id.btnNivel4)
        val btnN5 = findViewById<Button>(R.id.btnNivel5)
        val btnBack = findViewById<ImageButton>(R.id.btnBack)

        btnN1.setOnClickListener { iniciarNivel(1, 1200L) }
        btnN2.setOnClickListener { iniciarNivel(2, 1000L) }
        btnN3.setOnClickListener { iniciarNivel(3, 900L) }
        btnN4.setOnClickListener { iniciarNivel(4, 800L) }
        btnN5.setOnClickListener { iniciarNivel(5, 700L) }

        btnBack.setOnClickListener {
            finish()
        }
    }

    //Función mejorada
    fun iniciarNivel(nivel: Int, intervalo: Long) {
        val intent = Intent(this, GameplayActivity::class.java)
        intent.putExtra(GameplayActivity.EXTRA_MODO, GameplayActivity.MODO_NIVEL)
        intent.putExtra(GameplayActivity.EXTRA_INTERVALO, intervalo)
        intent.putExtra("nivel", nivel)
        startActivity(intent)
    }
}