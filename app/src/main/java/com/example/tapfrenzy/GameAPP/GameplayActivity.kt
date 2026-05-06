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

    companion object {
        const val EXTRA_MODO = "modo"
        const val EXTRA_INTERVALO = "intervalo_inicial"
        const val EXTRA_NIVEL = "nivel"

        const val MODO_ARCADE = "arcade"
        const val MODO_NIVEL = "nivel"
    }

    lateinit var tvPuntuacion: TextView
    lateinit var tvNivel: TextView
    lateinit var btnBack: ImageButton
    lateinit var agujeros: List<ImageButton>

    var puntuacion = 0
    var indiceTopoActual = -1
    var juegoActivo = true
    var intervalo = 1200L
    var modo = MODO_NIVEL
    var nivel = 1
    var topoGolpeado = false
    var duracion = 30000L

    val handler = android.os.Handler(android.os.Looper.getMainLooper())
    lateinit var gameRunnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_gameplay)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 🔹 Recibir datos
        modo = intent.getStringExtra(EXTRA_MODO) ?: MODO_NIVEL
        nivel = intent.getIntExtra(EXTRA_NIVEL, 1)
        intervalo = intent.getLongExtra(EXTRA_INTERVALO, 1200L)

        tvPuntuacion = findViewById(R.id.tvPuntuacion)
        tvNivel = findViewById(R.id.tvNivel)
        btnBack = findViewById(R.id.btnBack)

        // 🔹 Configurar modo
        if (modo == MODO_ARCADE) {
            intervalo = 1300L
            duracion = 45000L
            tvNivel.text = "Modo Arcade"
        } else {
            duracion = 20000L + (nivel * 5000L)
            tvNivel.text = "Nivel $nivel"
        }

        actualizarPuntuacion()

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

        // 🔹 Clicks
        agujeros.forEachIndexed { index, boton ->
            boton.setOnClickListener {
                if (index == indiceTopoActual && indiceTopoActual != -1) {

                    puntuacion += 10
                    topoGolpeado = true

                    actualizarPuntuacion()

                    agujeros[index]
                        .setImageResource(R.drawable.topo_golpeado)

                    handler.postDelayed({
                        ocultarTopo()
                    }, 200)
                }
            }
        }

        iniciarJuego()
        iniciarTemporizador()

        btnBack.setOnClickListener {
            finish()
        }
    }

    override fun onPause() {
        super.onPause()
        juegoActivo = false
        handler.removeCallbacks(gameRunnable)
    }

    fun iniciarJuego() {
        gameRunnable = object : Runnable {
            override fun run() {
                if (juegoActivo) {
                    mostrarTopo()

                    if (modo == MODO_ARCADE) {
                        intervalo -= 20
                        intervalo = maxOf(400L, intervalo)
                    }

                    handler.postDelayed(this, intervalo)
                }
            }
        }
        handler.post(gameRunnable)
    }

    fun mostrarTopo() {
        if (!juegoActivo) return

        ocultarTopo()

        indiceTopoActual = (0..8).random()
        topoGolpeado = false

        agujeros[indiceTopoActual].setImageResource(R.drawable.topo)

        handler.postDelayed({
            if (!topoGolpeado && indiceTopoActual != -1) {
                puntuacion -= 5
                actualizarPuntuacion()
            }

            ocultarTopo()

        }, 900)
    }

    fun ocultarTopo() {
        if (indiceTopoActual != -1) {

            agujeros[indiceTopoActual]
                .setImageResource(R.drawable.agujero)

            indiceTopoActual = -1
        }
    }

    fun actualizarPuntuacion() {
        tvPuntuacion.text = "Puntuación: $puntuacion"
    }

    fun iniciarTemporizador() {
        handler.postDelayed({
            juegoActivo = false
            ocultarTopo()

            android.app.AlertDialog.Builder(this)
                .setTitle("Fin del juego")
                .setMessage("Nivel: $nivel\nPuntuación: $puntuacion")
                .setPositiveButton("OK") { _, _ -> finish() }
                .show()

        }, duracion)
    }
}