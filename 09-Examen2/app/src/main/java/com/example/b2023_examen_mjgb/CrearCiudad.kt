package com.example.b2023_examen_mjgb

import android.annotation.SuppressLint
import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import com.google.firebase.firestore.FieldValue
//import kotlin.collections.ArrayList
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.math.log

class CrearCiudad : AppCompatActivity() {
    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_ciudad)

        val idPais = intent.getStringExtra("ID_PAIS")


        // logica para crear Pais
        val botonCrearCiudad = findViewById<Button>(R.id.btn_crear_ciudad)
        botonCrearCiudad.setOnClickListener {
            val nombre = findViewById<EditText>(R.id.input_nombre_ciudad).text.toString()
            val poblacion =
                findViewById<EditText>(R.id.input_poblacion_ciudad).getIntValueOrDefault()
            val esCapital = findViewById<CheckBox>(R.id.input_capital_ciudad).isChecked()
            val fechaFund = findViewById<EditText>(R.id.input_fecha_ciudad).text.toString()

            // crear pais
            val db = Firebase.firestore
            val referencia = db.collection("ciudades")
            val nuevaCiudadMap = hashMapOf(
                "nombre" to nombre,
                "poblacion" to poblacion,
                "esCapital" to esCapital,
                "fechaFund" to fechaFund
            )
            referencia
                .add(nuevaCiudadMap)
                .addOnSuccessListener {
                    val idCiudad = it.id
                    val referenciPais = db.collection("paises").document(idPais!!)
                    referenciPais.update(
                        "ciudades", FieldValue.arrayUnion(
                            hashMapOf(
                                "id" to idCiudad,
                                "nombre" to nombre,
                                "poblacion" to poblacion,
                                "esCapital" to esCapital,
                                "fechaFund" to fechaFund

                            )
                        )
                    )
                    adaptadorCiudad.notifyDataSetChanged()
                    finish()
                }
                .addOnFailureListener { }
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