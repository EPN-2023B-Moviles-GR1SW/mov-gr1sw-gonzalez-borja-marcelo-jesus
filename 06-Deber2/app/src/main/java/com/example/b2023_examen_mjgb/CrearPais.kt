package com.example.b2023_examen_mjgb

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class CrearPais : AppCompatActivity() {
    @SuppressLint("CutPasteId")
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
            val poblacion = findViewById<EditText>(R.id.input_poblacion_pais).getIntValueOrDefault()
            val tasa = findViewById<EditText>(R.id.input_tasa_pais).getDoubleValueOrDefault()

            val respuesta = EBaseDeDatos.tablaPais!!.crearPais(
                nombre,capital,poblacion,tasa
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