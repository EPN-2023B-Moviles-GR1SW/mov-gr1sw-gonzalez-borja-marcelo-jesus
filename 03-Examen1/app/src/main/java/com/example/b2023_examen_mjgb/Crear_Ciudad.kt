package com.example.b2023_examen_mjgb

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView

class Crear_Ciudad : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_ciudad)
        val idCiudad= intent.getIntExtra("ID_CIUDAD",-1)
        val idPais= intent.getIntExtra("ID_PAIS",-1)

        val inputIdCiudad = findViewById<EditText>(R.id.input_id_ciudad)
        inputIdCiudad.setText(idCiudad.toString())
        inputIdCiudad.isEnabled = false
        val inputIdPais = findViewById<EditText>(R.id.input_idpais_ciudad)
        inputIdPais.setText(idPais.toString())
        inputIdPais.isEnabled = false

        // logica para crear Pais
        val botonCrearCiudad = findViewById<Button>(R.id.btn_crear_ciudad)
        botonCrearCiudad.setOnClickListener {
            val id = findViewById<EditText>(R.id.input_id_ciudad).text.toString().toInt()
            val nombre = findViewById<EditText>(R.id.input_nombre_ciudad).text.toString()
            val idIPais = findViewById<EditText>(R.id.input_idpais_ciudad).text.toString().toInt()
            val nuevaCiudad = BCiudad(id, nombre, idIPais)
            BBaseDatosMemoria.arregloBCiudad.add(nuevaCiudad)
            //
            setResult(Activity.RESULT_OK)
            adaptadorCiudad.notifyDataSetChanged()
            //
            finish()
        }
    }
}