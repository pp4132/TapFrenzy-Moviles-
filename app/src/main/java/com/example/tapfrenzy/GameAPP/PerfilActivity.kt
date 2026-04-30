package com.example.tapfrenzy.GameAPP

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.tapfrenzy.R

class PerfilActivity : AppCompatActivity() {

    private lateinit var tvAlias: TextView
    private lateinit var tvNombre: TextView
    private lateinit var btnCerrarSesion: Button

    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        tvAlias = findViewById(R.id.tvAlias)
        tvNombre = findViewById(R.id.tvNombre)
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion)

        dbHelper = DBHelper(this)

        cargarDatosUsuario()

        btnCerrarSesion.setOnClickListener {
            cerrarSesion()
        }
    }

    private fun cargarDatosUsuario() {
        val prefs = getSharedPreferences("Sesion", Context.MODE_PRIVATE)
        val idUsuario = prefs.getInt("IdUsuario", -1)

        if (idUsuario == -1) {
            irALogin()
            return
        }

        val db: SQLiteDatabase = dbHelper.readableDatabase

        val cursor = db.rawQuery(
            "SELECT Nombre_completo, Alias FROM Usuarios WHERE Id_Usuario=?",
            arrayOf(idUsuario.toString())
        )

        if (cursor.moveToFirst()) {
            val nombre = cursor.getString(0)
            val alias = cursor.getString(1)

            tvNombre.text = "Nombre: $nombre"
            tvAlias.text = "Alias: $alias"
        }

        val cursor2 = db.rawQuery(
            "SELECT MAX(Puntaje) FROM Partidas WHERE Id_Usuario=?",
            arrayOf(idUsuario.toString())
        )

        if (cursor2.moveToFirst()) {
            val mejor = cursor2.getInt(0)
            // Mostrarlo en otro TextView
        }

        cursor2.close()

        cursor.close()
    }

    private fun cerrarSesion() {
        val prefs = getSharedPreferences("Sesion", Context.MODE_PRIVATE)
        prefs.edit().clear().apply()

        Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show()

        irALogin()
    }

    private fun irALogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}