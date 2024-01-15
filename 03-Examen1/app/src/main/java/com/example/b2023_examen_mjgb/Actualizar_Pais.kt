package com.example.b2023_examen_mjgb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class Actualizar_Pais : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_pais)
        val posicion = intent.getIntExtra("ID_PAIS",-1)
        val idPais = BBaseDatosMemoria.arregloBPais[posicion].id

        val inputIdAPais = findViewById<EditText>(R.id.input_a_id_pais)
        inputIdAPais.setText(idPais.toString())
        inputIdAPais.isEnabled = false

        val inputNombreAPais = findViewById<EditText>(R.id.input_a_nombre_pais)
        inputNombreAPais.setText(BBaseDatosMemoria.arregloBPais[posicion].nombre)

        val inputCapitalAPais = findViewById<EditText>(R.id.input_a_capital_pais)
        inputCapitalAPais.setText(BBaseDatosMemoria.arregloBPais[posicion].capital)

        // lógica para actualizar Pais
        val botonActualizarPais = findViewById<Button>(R.id.btn_actualizar_pais)
        botonActualizarPais.setOnClickListener {
            val nuevoNombre = inputNombreAPais.text.toString()
            val nuevaCapital = inputCapitalAPais.text.toString()

            BBaseDatosMemoria.arregloBPais[posicion].nombre = nuevoNombre
            BBaseDatosMemoria.arregloBPais[posicion].capital = nuevaCapital

            adaptadorPais.notifyDataSetChanged()
            finish()
        }
    }
}