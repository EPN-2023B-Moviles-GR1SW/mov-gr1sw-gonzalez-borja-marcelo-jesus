package com.example.b2023_examen_mjgb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Actualizar_Pais : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_pais)
        val idPais = intent.getStringExtra("ID_PAIS")
        val inputNombreAPais = findViewById<EditText>(R.id.input_a_nombre_pais)
        val inputCapitalAPais = findViewById<EditText>(R.id.input_a_capital_pais)
        val inputPoblacionAPais = findViewById<EditText>(R.id.input_a_poblacion_pais)
        val inputTasaAPais = findViewById<EditText>(R.id.input_a_tasa_pais)


        var pais: BPais? = null
        if (idPais != null) {
            consultarPaisId(idPais, object : PaisCallback {
                override fun onCallback(result: BPais) {
                    pais = result
                    inputNombreAPais.setText(pais!!.nombre)
                    inputCapitalAPais.setText(pais!!.capital)
                    inputPoblacionAPais.setText(pais!!.poblacion.toString())
                    inputTasaAPais.setText(pais!!.tasaCrecimiento.toString())


                    // logica
                    val botonActualizarPais = findViewById<Button>(R.id.btn_actualizar_pais)
                    botonActualizarPais.setOnClickListener {
                        pais.let {
                            it?.nombre = inputNombreAPais.text.toString()
                            it?.capital = inputCapitalAPais.text.toString()
                            it?.poblacion = inputPoblacionAPais.getIntValueOrDefault()
                            it?.tasaCrecimiento = inputTasaAPais.getDoubleValueOrDefault()

                            //actualizar pais
                            val db = Firebase.firestore
                            val referencia = db.collection("paises").document(pais?.id!!)

                            val nuevoPais = hashMapOf(
                                "nombre" to it?.nombre,
                                "capital" to it?.capital,
                                "poblacion" to it?.poblacion,
                                "tasaCrecimiento" to it?.tasaCrecimiento
                            )

                            referencia
                                .update(nuevoPais as Map<String, Any>)
                                .addOnSuccessListener {
                                    adaptadorPais.notifyDataSetChanged()
                                }
                                .addOnFailureListener { }
                            finish()
                        }
                    }
                    // fin logica
                }
            })
        }

        // lógica para actualizar Pais

    }

    interface PaisCallback {
        fun onCallback(pais: BPais)
    }

    fun consultarPaisId(idPais: String, paisCallback: PaisCallback) {
        val db = Firebase.firestore
        val referencia = db.collection("paises")
        referencia
            .document(idPais)
            .get()
            .addOnSuccessListener {
                var pais = BPais(
                    it.id as String?,
                    it.data?.get("nombre") as String?,
                    it.data?.get("capital") as String?,
                    (it.data?.get("poblacion") as? Long)?.toInt(),
                    it.data?.get("tasaCrecimiento") as? Double?,
                    it.data?.get("ciudades") as ArrayList<BCiudad>?,
                )
                paisCallback.onCallback(pais)
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