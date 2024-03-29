package com.example.b2023_examen_mjgb

import android.annotation.SuppressLint
import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText

class CrearCiudad : AppCompatActivity() {
    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_ciudad)
        val idCiudad= intent.getIntExtra("ID_CIUDAD",-1)
        val idPais= intent.getIntExtra("ID_PAIS",-1)

        val inputIdCiudad = findViewById<EditText>(R.id.input_id_ciudad)
        inputIdCiudad.setText(idCiudad.toString())
        inputIdCiudad.isEnabled = false
        val inputIdPais = findViewById<EditText>(R.id.input_idpais_ciudad)
        inputIdPais.setText(idPais.toString())
        inputIdPais.isEnabled = false

        // logica para crear Pais
        val botonCrearCiudad = findViewById<Button>(R.id.btn_crear_ciudad)
        botonCrearCiudad.setOnClickListener {
            val id = findViewById<EditText>(R.id.input_id_ciudad).text.toString().toInt()
            val nombre = findViewById<EditText>(R.id.input_nombre_ciudad).text.toString()
            val idIPais = findViewById<EditText>(R.id.input_idpais_ciudad).text.toString().toInt()
            val poblacion = findViewById<EditText>(R.id.input_poblacion_ciudad).getIntValueOrDefault()
            val esCapital = findViewById<CheckBox>(R.id.input_capital_ciudad).isChecked()
            val fechaFundacion = findViewById<EditText>(R.id.input_fecha_ciudad).text.toString()

            val respuesta = EBaseDeDatos.tablaCiudad!!.crearCiudad(
                nombre,idIPais,poblacion,esCapital,fechaFundacion
            )
            if (respuesta){
                Log.d("DEBUG", "esCapital: $esCapital")
                adaptadorCiudad.notifyDataSetChanged()
                setResult(Activity.RESULT_OK)
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