package com.example.tapfrenzy.GameAPP

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) :
    SQLiteOpenHelper(context, "TapFrenzy.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {

        db.execSQL("""
            CREATE TABLE Usuarios (
                Id_Usuario INTEGER PRIMARY KEY AUTOINCREMENT,
                Nombre_completo TEXT,
                Alias TEXT UNIQUE,
                Contrasena TEXT,
                Fecha_nacimiento TEXT,
                Fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP
            )
        """)

        db.execSQL("""
            CREATE TABLE Partidas (
                Id_Partida INTEGER PRIMARY KEY AUTOINCREMENT,
                Id_Usuario INTEGER,
                Puntaje INTEGER,
                Nivel INTEGER,
                N_Topos INTEGER,
                Modo TEXT,
                Fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (Id_Usuario) REFERENCES Usuarios(Id_Usuario)
            )
        """)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS Partidas")
        db.execSQL("DROP TABLE IF EXISTS Usuarios")
        onCreate(db)
    }

    fun insertarUsuario(
        nombre: String,
        alias: String,
        contrasena: String,
        fechaNacimiento: String
    ): Long {
        val db = writableDatabase

        val values = android.content.ContentValues().apply {
            put("Nombre_completo", nombre)
            put("Alias", alias)
            put("Contrasena", contrasena)
            put("Fecha_nacimiento", fechaNacimiento)
        }

        return db.insert("Usuarios", null, values)
    }

    fun existeAlias(alias: String): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT 1 FROM Usuarios WHERE Alias = ?",
            arrayOf(alias)
        )

        val existe = cursor.moveToFirst()
        cursor.close()
        return existe
    }

    fun hashPassword(password: String): String {
        val bytes = java.security.MessageDigest
            .getInstance("SHA-256")
            .digest(password.toByteArray())

        return bytes.joinToString("") { "%02x".format(it) }
    }
}