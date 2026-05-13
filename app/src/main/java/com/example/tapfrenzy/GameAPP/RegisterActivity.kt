package com.example.tapfrenzy.GameAPP

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tapfrenzy.R

class RegisterActivity : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        dbHelper = DBHelper(this)

        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etAlias = findViewById<EditText>(R.id.etAlias)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val etFecha = findViewById<EditText>(R.id.etFechaNacimiento)
        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)
        val btnBack = findViewById<ImageButton>(R.id.btnBack)

        btnBack.setOnClickListener {
            finish()
        }

        btnRegistrar.setOnClickListener {

            val nombre = etNombre.text.toString().trim()
            val alias = etAlias.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val fecha = etFecha.text.toString().trim()



            // 🔍 Validaciones básicas
            if (nombre.isEmpty() || alias.isEmpty() || password.isEmpty() || fecha.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 🔁 Validar alias existente
            if (dbHelper.existeAlias(alias)) {
                Toast.makeText(this, "El alias ya está en uso", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 🔐 Encriptar contraseña
            val passwordHash = dbHelper.hashPassword(password)

            // 💾 Insertar usuario
            val resultado = dbHelper.insertarUsuario(
                nombre,
                alias,
                passwordHash,
                fecha
            )

            if (resultado != -1L) {
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()

                // 👉 Opcional: limpiar campos
                etNombre.text.clear()
                etAlias.text.clear()
                etPassword.text.clear()
                etFecha.text.clear()

                // 👉 Opcional: cerrar pantalla
                finish()

            } else {
                Toast.makeText(this, "Error al registrar usuario", Toast.LENGTH_SHORT).show()
            }
        }
    }
}