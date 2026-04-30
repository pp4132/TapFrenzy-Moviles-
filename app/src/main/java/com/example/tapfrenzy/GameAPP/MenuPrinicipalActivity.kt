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

class MenuPrinicipalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("Sesion", MODE_PRIVATE)
        val idUsuario = prefs.getInt("IdUsuario", -1)

        if (idUsuario == -1) {
            // No hay usuario → abrir pantalla de registro/login
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        enableEdgeToEdge()
        setContentView(R.layout.activity_menu_prinicipal)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnNiveles = findViewById<Button>(R.id.btnNiveles)
        val btnArcade = findViewById<Button>(R.id.btnArcade)
        val btnSalir = findViewById<Button>(R.id.btnSalir)
        val btnPerfil = findViewById<ImageButton>(R.id.btnPerfil)

        btnPerfil.setOnClickListener {
            val intent = Intent(this, PerfilActivity::class.java)
            startActivity(intent)
        }

        btnNiveles.setOnClickListener {
            val intent = Intent(this, LevelsActivity::class.java)
            startActivity(intent)
        }

        btnArcade.setOnClickListener {
            val intent = Intent(this, ArcadeActivity::class.java)
            startActivity(intent)
        }

        btnSalir.setOnClickListener {
            finish()
        }

    }
}