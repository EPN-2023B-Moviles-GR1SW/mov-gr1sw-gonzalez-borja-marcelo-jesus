package com.example.b2023_examen_mjgb

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
//import kotlin.collections.ArrayList
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.math.log

class CrearPais : AppCompatActivity() {
    //val arregloCiudades: ArrayList<BCiudad> = arrayListOf()
    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_pais)

        // logica para crear Pais
        val botonCrearPais = findViewById<Button>(R.id.btn_crear_pais)
        botonCrearPais.setOnClickListener {
            val nombre = findViewById<EditText>(R.id.input_nombre_pais).text.toString()
            val capital = findViewById<EditText>(R.id.input_capital_pais).text.toString()
            val poblacion = findViewById<EditText>(R.id.input_poblacion_pais).getIntValueOrDefault()
            val tasa = findViewById<EditText>(R.id.input_tasa_pais).getDoubleValueOrDefault()
            val ciudadesDelPais = arrayListOf<BCiudad>()

            // crear pais
            val db = Firebase.firestore
            val referencia = db.collection("paises")
            val ciudadesMap = ciudadesDelPais.map{ ciudad ->
                mapOf(
                    "nombre" to ciudad.nombre,
                    "poblacion" to ciudad.poblacion,
                    "esCapital" to ciudad.esCapital,
                    "fechaFund" to ciudad.fechaFund
                )
            }
            val nuevoPais = hashMapOf(
                "nombre" to nombre,
                "capital" to capital,
                "poblacion" to poblacion,
                "tasaCrecimiento" to tasa,
                "ciudades" to ciudadesMap
            )

            referencia
                .add(nuevoPais)
                .addOnSuccessListener {
                    adaptadorPais.notifyDataSetChanged()
                }
                .addOnFailureListener {  }
            adaptadorPais.notifyDataSetChanged()
            finish()
        }
    }

    private fun EditText.getIntValueOrDefault(defaultValue: Int = 0): Int {
        return try {
            text.toString().toInt()
        } catch (e: NumberFormatException) {
            defaultValue
        }
    }

    private fun EditText.getDoubleValueOrDefault(defaultValue: Double = 0.0): Double {
        return try {
            text.toString().toDouble()
        } catch (e: NumberFormatException) {
            defaultValue
        }
    }
}