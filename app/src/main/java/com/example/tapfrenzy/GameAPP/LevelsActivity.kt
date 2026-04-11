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
        enableEdgeToEdge()
        setContentView(R.layout.activity_levels)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnBack = findViewById<ImageButton>(R.id.btnBack)

        val btnN1 = findViewById<Button>(R.id.btnNivel1)
        val btnN2 = findViewById<Button>(R.id.btnNivel2)
        val btnN3 = findViewById<Button>(R.id.btnNivel3)
        val btnN4 = findViewById<Button>(R.id.btnNivel4)
        val btnN5 = findViewById<Button>(R.id.btnNivel5)

        //Lista de niveles (botón, número de nivel, intervalo)
        val niveles = listOf(
            Triple(btnN1, 1, 1200L),
            Triple(btnN2, 2, 1000L),
            Triple(btnN3, 3, 900L),
            Triple(btnN4, 4, 800L),
            Triple(btnN5, 5, 700L)
        )

        //Asignar eventos a todos los botones
        niveles.forEach { (boton, nivel, intervalo) ->
            boton.setOnClickListener {
                boton.isEnabled = false // evita doble click
                iniciarNivel(nivel, intervalo)
            }
        }

        //Botón regresar
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