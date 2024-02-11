package com.example.deber3_gr1sw_mjbg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val botonIniciarToDoList = findViewById<Button>(R.id.btn_ir_app)
        botonIniciarToDoList.setOnClickListener {
            irActividad(FRecyclerViewProyectos::class.java)
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.Hoy)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.Hoy -> {
                    irActividad(MainActivity::class.java)
                    true
                }
                R.id.Bandeja -> {
                    irActividad(FRecyclerViewTareas::class.java)
                    true
                }
                R.id.Explorar -> {
                    irActividad(FRecyclerViewProyectos::class.java)
                    true
                }
                else -> false
            }
        }
    }

    fun irActividad(clase: Class<*>){
        val intent = Intent(this,clase)
        startActivity(intent)
    }
}