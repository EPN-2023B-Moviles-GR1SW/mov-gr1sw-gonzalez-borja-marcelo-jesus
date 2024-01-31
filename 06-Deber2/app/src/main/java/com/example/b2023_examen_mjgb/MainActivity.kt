package com.example.b2023_examen_mjgb

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        //Base de datos sqlite
        EBaseDeDatos.tablaPais = ESqliteHelperPais(this)
        val botonIniciarPais = findViewById<Button>(R.id.btn_iniciar_paises)
        botonIniciarPais.setOnClickListener {
            irActividad(Actividad_Pais::class.java)
        }
    }

    fun irActividad(clase: Class<*>){
        val intent = Intent(this,clase)
        startActivity(intent)
    }
}
