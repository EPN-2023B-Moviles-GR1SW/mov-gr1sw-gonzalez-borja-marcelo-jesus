package com.example.b2023_examen_mjgb

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.HashMap

class ActualizarCiudad : AppCompatActivity() {
    var arregloCiudades: ArrayList<BCiudad> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_ciudad)

        val idCiudad = intent.getStringExtra("ID_CIUDAD")
        val idPais = intent.getStringExtra("ID_PAIS")
        consultarCiudadesDelPais(idPais!!)


        val inputNombreACiudad = findViewById<EditText>(R.id.input_a_nombre_ciudad)
        val inputPoblacionACiudad = findViewById<EditText>(R.id.input_a_poblacion_ciudad)
        val inputEsCapitalACiudad = findViewById<CheckBox>(R.id.input_a_captal_ciudad)
        val inputFechaACiudad = findViewById<EditText>(R.id.input_a_fecha_ciudad)

        // llenar campos
        var ciudad: BCiudad? = null
        if (idCiudad != null) {
            consultarCiudad(idCiudad, object : CiudadCallback {
                override fun onCallback(result: BCiudad) {
                    ciudad = result
                    inputNombreACiudad.setText(ciudad!!.nombre)
                    inputPoblacionACiudad.setText(ciudad!!.poblacion.toString())
                    if (ciudad!!.esCapital!!) {
                        inputEsCapitalACiudad.isChecked = true
                    }
                    inputFechaACiudad.setText(ciudad!!.fechaFund)

                    // lógica para actualizar Ciudad
                    val botonActualizarCiudad = findViewById<Button>(R.id.btn_actualizar_ciudad)
                    botonActualizarCiudad.setOnClickListener {
                        ciudad.let {
                            it?.nombre = inputNombreACiudad.text.toString()
                            it?.poblacion = inputPoblacionACiudad.getIntValueOrDefault()
                            it?.esCapital = inputEsCapitalACiudad.isChecked()
                            it?.fechaFund = inputFechaACiudad.text.toString()

                            val db = Firebase.firestore
                            val referenciaCiudad = db.collection("ciudades").document(ciudad?.id!!)
                            val referenciaPais = db.collection("paises").document(idPais)

                            val nuevaCiudad = hashMapOf(
                                "nombre" to it?.nombre,
                                "poblacion" to it?.poblacion,
                                "esCapital" to it?.esCapital,
                                "fechaFund" to it?.fechaFund
                            )

                            referenciaCiudad
                                .update(nuevaCiudad as Map<String, Any>)
                                .addOnSuccessListener {
                                    for (ciudaPais in arregloCiudades) {
                                        if (ciudaPais.id == ciudad!!.id) {
                                            arregloCiudades[arregloCiudades.indexOf(ciudaPais)] =
                                                ciudad!!
                                        }
                                    }
                                    referenciaPais
                                        .update("ciudades", arregloCiudades)
                                        .addOnSuccessListener { adaptadorCiudad.notifyDataSetChanged() }
                                        .addOnFailureListener { }
                                }
                        }
                        adaptadorCiudad.notifyDataSetChanged()
                        finish()
                    }

                    // fin logica
                }
            })
        }
    }

    interface CiudadCallback {
        fun onCallback(ciudad: BCiudad)
    }

    fun consultarCiudad(idCiudad: String, ciudadCallback: CiudadCallback) {
        val db = Firebase.firestore
        val referencia = db.collection("ciudades")
        referencia
            .document(idCiudad)
            .get()
            .addOnSuccessListener {
                var ciudad = BCiudad(
                    it.id as String?,
                    it.data?.get("nombre") as String?,
                    (it.data?.get("poblacion") as Long).toInt(),
                    it.data?.get("esCapital") as Boolean?,
                    it.data?.get("fechaFund") as String?,
                )
                ciudadCallback.onCallback(ciudad)
            }
    }


    fun consultarCiudadesDelPais(idPais: String) {
        val db = Firebase.firestore
        val referencia = db.collection("paises").document(idPais)
        adaptadorCiudad.notifyDataSetChanged()
        referencia
            .get()
            .addOnSuccessListener {
                val ciudades = it.data?.get("ciudades") as ArrayList<HashMap<String, Any>>
                if (ciudades != null) {
                    for (ciudad in ciudades) {
                        val ciudadObj = BCiudad(
                            ciudad["id"] as String,
                            ciudad["nombre"] as String,
                            (ciudad["poblacion"] as Long).toInt(),
                            ciudad["esCapital"] as Boolean,
                            ciudad["fechaFund"] as String
                        )
                        arregloCiudades.add(ciudadObj)
                    }
                }
                adaptadorCiudad.notifyDataSetChanged()
            }
            .addOnFailureListener { }
    }

    fun EditText.getIntValueOrDefault(defaultValue: Int = 0): Int {
        return try {
            text.toString().toInt()
        } catch (e: NumberFormatException) {
            defaultValue
        }
    }
}