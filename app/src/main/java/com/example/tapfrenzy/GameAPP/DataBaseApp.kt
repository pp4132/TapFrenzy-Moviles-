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
        // Por ahora vacío
    }
}