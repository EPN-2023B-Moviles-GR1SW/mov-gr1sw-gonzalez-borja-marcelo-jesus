package com.example.deber3_gr1sw_mjbg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val botonIniciarToDoList = findViewById<Button>(R.id.btn_ir_app)
        botonIniciarToDoList.setOnClickListener {
            irActividad(FRecyclerViewProyectos::class.java)
        }
    }

    fun irActividad(clase: Class<*>){
        val intent = Intent(this,clase)
        startActivity(intent)
    }
}