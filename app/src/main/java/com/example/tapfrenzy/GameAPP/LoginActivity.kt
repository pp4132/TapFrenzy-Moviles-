package com.example.tapfrenzy.GameAPP

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.tapfrenzy.R

class LoginActivity : AppCompatActivity() {

    lateinit var etAlias: EditText
    lateinit var etPassword: EditText
    lateinit var btnLogin: Button
    lateinit var btnRegistro: Button

    lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        dbHelper = DBHelper(this)

        etAlias = findViewById(R.id.etAlias)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnRegistro = findViewById(R.id.btnRegistro)

        btnLogin.setOnClickListener {
            login()
        }

        btnRegistro.setOnClickListener {
            registrar()
        }
    }

    private fun login() {
        val alias = etAlias.text.toString()
        val pass = etPassword.text.toString()

        val db = dbHelper.readableDatabase

        val cursor = db.rawQuery(
            "SELECT Id_Usuario FROM Usuarios WHERE Alias=? AND Contrasena=?",
            arrayOf(alias, pass)
        )

        if (cursor.moveToFirst()) {
            val idUsuario = cursor.getInt(0)

            guardarSesion(idUsuario, alias)

            Toast.makeText(this, "Bienvenido $alias", Toast.LENGTH_SHORT).show()

            startActivity(Intent(this, MenuPrinicipalActivity::class.java))
            finish()
        } else {
            Toast.makeText(this, "Datos incorrectos", Toast.LENGTH_SHORT).show()
        }

        cursor.close()
    }

    private fun registrar() {
        val alias = etAlias.text.toString().trim()
        val pass = etPassword.text.toString().trim()

        if (alias.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        if (pass.length < 4) {
            Toast.makeText(this, "La contraseña debe tener al menos 4 caracteres", Toast.LENGTH_SHORT).show()
            return
        }

        val db = dbHelper.writableDatabase

        // Verificar si el alias ya existe
        val cursor = db.rawQuery(
            "SELECT Id_Usuario FROM Usuarios WHERE Alias=?",
            arrayOf(alias)
        )

        if (cursor.moveToFirst()) {
            Toast.makeText(this, "El alias ya está en uso", Toast.LENGTH_SHORT).show()
            cursor.close()
            return
        }
        cursor.close()

        val values = ContentValues().apply {
            put("Nombre_completo", alias) // luego puedes cambiar esto
            put("Alias", alias)
            put("Contrasena", pass)
        }

        val resultado = db.insert("Usuarios", null, values)

        if (resultado != -1L) {
            Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show()

            // 🔥 Opcional: loguear automáticamente después de registrar
            guardarSesion(resultado.toInt(), alias)

            startActivity(Intent(this, MenuPrinicipalActivity::class.java))
            finish()

        } else {
            Toast.makeText(this, "Error al registrar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun guardarSesion(idUsuario: Int, alias: String) {
        val prefs = getSharedPreferences("Sesion", Context.MODE_PRIVATE)
        prefs.edit().apply {
            putInt("IdUsuario", idUsuario)
            putString("Alias", alias)
            apply()
        }
    }
}