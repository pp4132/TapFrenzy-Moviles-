package com.example.tapfrenzy.GameAPP

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.tapfrenzy.R
import com.example.tapfrenzy.GameAPP.DBHelper
import com.example.tapfrenzy.GameAPP.RegisterActivity

class LoginActivity : AppCompatActivity() {

    lateinit var etAlias: EditText
    lateinit var etPassword: EditText
    lateinit var btnLogin: Button
    lateinit var btnRegistro: Button

    lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val prefs = getSharedPreferences("Sesion", Context.MODE_PRIVATE)
        val idUsuario = prefs.getInt("IdUsuario", -1)

        if (idUsuario != -1) {
            startActivity(Intent(this, MenuPrinicipalActivity::class.java))
            finish()
        }
        dbHelper = DBHelper(this)

        etAlias = findViewById(R.id.etAlias)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnRegistro = findViewById(R.id.btnRegistro)

        btnLogin.setOnClickListener {
            login()
        }

        btnRegistro.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun login() {
        val alias = etAlias.text.toString().trim()
        val pass = etPassword.text.toString().trim()

        if (alias.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val passHash = dbHelper.hashPassword(pass)

        val db = dbHelper.readableDatabase

        val cursor = db.rawQuery(
            "SELECT Id_Usuario FROM Usuarios WHERE Alias=? AND Contrasena=?",
            arrayOf(alias, passHash)
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


    private fun guardarSesion(idUsuario: Int, alias: String) {
        val prefs = getSharedPreferences("Sesion", Context.MODE_PRIVATE)
        prefs.edit().apply {
            putInt("IdUsuario", idUsuario)
            putString("Alias", alias)
            apply()
        }
    }
}