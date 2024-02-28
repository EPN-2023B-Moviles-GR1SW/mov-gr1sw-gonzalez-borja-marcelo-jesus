package com.example.b2023_examen_mjgb

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText

class ActualizarCiudad : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_ciudad)

        val idCiudadArreglo= intent.getIntExtra("ID_CIUDAD",-1)
        val idPais= intent.getIntExtra("ID_PAIS",-1)

        val inputIdCiudad = findViewById<EditText>(R.id.input_a_id_ciudad)
        inputIdCiudad.isEnabled = false

        val inputNombreACiudad= findViewById<EditText>(R.id.input_a_nombre_ciudad)

        val inputIdPais = findViewById<EditText>(R.id.input_a_idPais_ciudad)
        inputIdPais.setText(idPais.toString())
        inputIdPais.isEnabled = false

        val inputPoblacionACiudad= findViewById<EditText>(R.id.input_a_poblacion_ciudad)

        val inputEsCapitalACiudad = findViewById<CheckBox>(R.id.input_a_captal_ciudad)

        val inputFechaACiudad = findViewById<EditText>(R.id.input_a_fecha_ciudad)

        // lógica para actualizar Ciudad
        val botonActualizarCiudad = findViewById<Button>(R.id.btn_actualizar_ciudad)
        botonActualizarCiudad.setOnClickListener {
            val nuevoNombre = inputNombreACiudad.text.toString()
            val nuevaPoblacion = inputPoblacionACiudad.getIntValueOrDefault()
            val nuevoEsCapital = inputEsCapitalACiudad.isChecked()
            val nuevaFechaFun = inputFechaACiudad.text.toString()


            setResult(Activity.RESULT_OK)
            adaptadorCiudad.notifyDataSetChanged()
            finish()
        }
    }
    fun EditText.getIntValueOrDefault(defaultValue: Int = 0): Int {
        return try {
            text.toString().toInt()
        } catch (e: NumberFormatException) {
            defaultValue
        }
    }
}