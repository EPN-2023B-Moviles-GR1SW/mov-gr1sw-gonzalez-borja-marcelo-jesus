package com.example.b2023_examen_mjgb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class Crear_Pais : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_pais)
        val idPais= intent.getIntExtra("ID_PAIS",-1)
        val inputIdPais = findViewById<EditText>(R.id.input_id_pais)
        inputIdPais.setText(idPais.toString())
        inputIdPais.isEnabled = false
        // logica para crear Pais
        val botonCrearPais = findViewById<Button>(R.id.btn_crear_pais)
        botonCrearPais.setOnClickListener {
            val id = findViewById<EditText>(R.id.input_id_pais).text.toString().toInt()
            val nombre = findViewById<EditText>(R.id.input_nombre_pais).text.toString()
            val capital = findViewById<EditText>(R.id.input_capital_pais).text.toString()
            val nuevoPais = BPais(id, nombre, capital)
            BBaseDatosMemoria.arregloBPais.add(nuevoPais)
            adaptadorPais.notifyDataSetChanged()
            finish()
        }
    }
}