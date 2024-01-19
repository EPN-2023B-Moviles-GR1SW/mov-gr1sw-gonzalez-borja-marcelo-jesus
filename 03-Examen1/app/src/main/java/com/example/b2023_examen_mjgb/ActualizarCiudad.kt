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
        inputIdCiudad.setText(BBaseDatosMemoria.arregloBCiudad[idCiudadArreglo].id.toString())
        inputIdCiudad.isEnabled = false

        val inputNombreACiudad= findViewById<EditText>(R.id.input_a_nombre_ciudad)
        inputNombreACiudad.setText(BBaseDatosMemoria.arregloBCiudad[idCiudadArreglo].nombre)

        val inputIdPais = findViewById<EditText>(R.id.input_a_idPais_ciudad)
        inputIdPais.setText(idPais.toString())
        inputIdPais.isEnabled = false

        val inputPoblacionACiudad= findViewById<EditText>(R.id.input_a_poblacion_ciudad)
        inputPoblacionACiudad.setText(BBaseDatosMemoria.arregloBCiudad[idCiudadArreglo].poblacion.toString())

        val inputEsCapitalACiudad = findViewById<CheckBox>(R.id.input_a_captal_ciudad)
        inputEsCapitalACiudad.isChecked = BBaseDatosMemoria.arregloBCiudad[idCiudadArreglo].esCapital

        val inputFechaACiudad = findViewById<EditText>(R.id.input_a_fecha_ciudad)
        inputFechaACiudad.setText(BBaseDatosMemoria.arregloBCiudad[idCiudadArreglo].fechaFund)

        // lógica para actualizar Ciudad
        val botonActualizarCiudad = findViewById<Button>(R.id.btn_actualizar_ciudad)
        botonActualizarCiudad.setOnClickListener {
            val nuevoNombre = inputNombreACiudad.text.toString()
            val nuevaPoblacion = inputPoblacionACiudad.getIntValueOrDefault()
            val nuevoEsCapital = inputEsCapitalACiudad.isChecked()
            val nuevaFechaFun = inputFechaACiudad.text.toString()

            BBaseDatosMemoria.arregloBCiudad[idCiudadArreglo].nombre = nuevoNombre
            BBaseDatosMemoria.arregloBCiudad[idCiudadArreglo].poblacion = nuevaPoblacion
            BBaseDatosMemoria.arregloBCiudad[idCiudadArreglo].esCapital = nuevoEsCapital
            BBaseDatosMemoria.arregloBCiudad[idCiudadArreglo].fechaFund = nuevaFechaFun
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