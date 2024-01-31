package com.example.b2023_examen_mjgb

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText

class ActualizarCiudad : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_ciudad)
        val posicion= intent.getIntExtra("ID_CIUDAD",-1)
        val idPais= intent.getIntExtra("ID_PAIS",-1)

        val arregloCiudades = EBaseDeDatos.tablaCiudad!!.consultarCiudades()
        val idCiudad = arregloCiudades[posicion].id

        val inputIdCiudad = findViewById<EditText>(R.id.input_a_id_ciudad)
        inputIdCiudad.setText(idCiudad.toString())
        inputIdCiudad.isEnabled = false

        val inputNombreACiudad= findViewById<EditText>(R.id.input_a_nombre_ciudad)
        inputNombreACiudad.setText(arregloCiudades[posicion].nombre)

        val inputIdPais = findViewById<EditText>(R.id.input_a_idPais_ciudad)
        inputIdPais.setText(idPais.toString())
        inputIdPais.isEnabled = false

        val inputPoblacionACiudad= findViewById<EditText>(R.id.input_a_poblacion_ciudad)
        inputPoblacionACiudad.setText(arregloCiudades[posicion].poblacion.toString())

        val inputEsCapitalACiudad = findViewById<CheckBox>(R.id.input_a_captal_ciudad)
        inputEsCapitalACiudad.isChecked = arregloCiudades[posicion].esCapital
        Log.d("DEBUG", "esCapital: "+arregloCiudades[posicion].esCapital)
        val inputFechaACiudad = findViewById<EditText>(R.id.input_a_fecha_ciudad)
        inputFechaACiudad.setText(arregloCiudades[posicion].fechaFund)

        // lógica para actualizar Ciudad
        val botonActualizarCiudad = findViewById<Button>(R.id.btn_actualizar_ciudad)
        botonActualizarCiudad.setOnClickListener {
            val nuevoNombre = inputNombreACiudad.text.toString()
            val nuevaPoblacion = inputPoblacionACiudad.getIntValueOrDefault()
            val nuevoEsCapital = inputEsCapitalACiudad.isChecked()
            val nuevaFechaFun = inputFechaACiudad.text.toString()

            val respuesta = EBaseDeDatos.tablaCiudad!!.actualizarCiudadFormulario(
                nuevoNombre,idPais,nuevaPoblacion,nuevoEsCapital,nuevaFechaFun,idCiudad
            )
            if (respuesta){
                setResult(Activity.RESULT_OK)
                adaptadorCiudad.notifyDataSetChanged()
            }
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