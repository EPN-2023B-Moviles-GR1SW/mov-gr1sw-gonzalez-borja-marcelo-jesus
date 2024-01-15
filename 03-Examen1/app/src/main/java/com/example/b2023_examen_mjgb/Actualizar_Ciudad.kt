package com.example.b2023_examen_mjgb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class Actualizar_Ciudad : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_ciudad)

        val idCiudadArreglo= intent.getIntExtra("ID_CIUDAD",-1)
        val idPais= intent.getIntExtra("ID_PAIS",-1)

        val inputIdCiudad = findViewById<EditText>(R.id.input_a_id_ciudad)
        inputIdCiudad.setText(BBaseDatosMemoria.arregloBCiudad[idCiudadArreglo].id.toString())
        inputIdCiudad.isEnabled = false

        val inputNombreACiudad= findViewById<EditText>(R.id.input_a_nombre_ciudad)
        inputNombreACiudad.setText(BBaseDatosMemoria.arregloBCiudad[idCiudadArreglo].nombre)

        val inputIdPais = findViewById<EditText>(R.id.input_a_idPais_ciudad)
        inputIdPais.setText(idPais.toString())
        inputIdPais.isEnabled = false

        // lógica para actualizar Ciudad
        val botonActualizarCiudad = findViewById<Button>(R.id.btn_actualizar_ciudad)
        botonActualizarCiudad.setOnClickListener {
            val nuevoNombre = inputNombreACiudad.text.toString()

            BBaseDatosMemoria.arregloBCiudad[idCiudadArreglo].nombre = nuevoNombre

            adaptadorCiudad.notifyDataSetChanged()
            finish()
        }
    }
}