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
        val arregloPaises = EBaseDeDatos.tablaPais!!.consultarPaises()

        val idPais = arregloPaises[posicion].id

        val inputIdAPais = findViewById<EditText>(R.id.input_a_id_pais)
        inputIdAPais.setText(idPais.toString())
        inputIdAPais.isEnabled = false

        val inputNombreAPais = findViewById<EditText>(R.id.input_a_nombre_pais)
        inputNombreAPais.setText(arregloPaises[posicion].nombre)

        val inputCapitalAPais = findViewById<EditText>(R.id.input_a_capital_pais)
        inputCapitalAPais.setText(arregloPaises[posicion].capital)

        val inputPoblacionAPais = findViewById<EditText>(R.id.input_a_poblacion_pais)
        inputPoblacionAPais.setText(arregloPaises[posicion].poblacion.toString())

        val inputTasaAPais = findViewById<EditText>(R.id.input_a_tasa_pais)
        inputTasaAPais.setText(arregloPaises[posicion].tasaCrecimiento.toString())

        // lógica para actualizar Pais
        val botonActualizarPais = findViewById<Button>(R.id.btn_actualizar_pais)
        botonActualizarPais.setOnClickListener {
            val nuevoNombre = inputNombreAPais.text.toString()
            val nuevaCapital = inputCapitalAPais.text.toString()
            val nuevaPoblacion = inputPoblacionAPais.getIntValueOrDefault()
            val nuevaTasa = inputTasaAPais.getDoubleValueOrDefault()
            val respuesta = EBaseDeDatos.tablaPais!!.actualizarPaisFormulario(
                nuevoNombre,nuevaCapital,nuevaPoblacion,nuevaTasa,idPais
            )
            if (respuesta){
                adaptadorPais.notifyDataSetChanged()
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

    fun EditText.getDoubleValueOrDefault(defaultValue: Double = 0.0): Double {
        return try {
            text.toString().toDouble()
        } catch (e: NumberFormatException) {
            defaultValue
        }
    }
}