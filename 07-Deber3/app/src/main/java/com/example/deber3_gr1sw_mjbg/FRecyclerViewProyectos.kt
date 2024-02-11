package com.example.deber3_gr1sw_mjbg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class FRecyclerViewProyectos : AppCompatActivity() {
    var totalTareas = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frecycler_view_proyectos)
        inicializarRecyclerView()
        val totalTareasTextView = findViewById<TextView>(R.id.tv_total_tareas)
        totalTareasTextView.text = totalTareas.toString()

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.Explorar)
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

    fun inicializarRecyclerView(){
        val recyclerView = findViewById<RecyclerView>(R.id.rv_proyectos)
        val adaptador = FRecyclerAdaptadorProyectos(
            this, // Contexto
            BBaseDatosMemoria.arregloBProyectos, // Arreglo datos
            recyclerView // Recycler view
        )
        recyclerView.adapter = adaptador
        recyclerView.itemAnimator = androidx.recyclerview.widget
            .DefaultItemAnimator()
        recyclerView.layoutManager = androidx.recyclerview.widget
            .LinearLayoutManager(this)
        adaptador.notifyDataSetChanged()
    }

    fun aumentarTotalTareas (){
        totalTareas = totalTareas + 1
        val totalTareasTextView = findViewById<TextView>(R.id.tv_total_tareas)
        totalTareasTextView.text = totalTareas.toString()
    }

    fun irActividad(clase: Class<*>){
        val intent = Intent(this,clase)
        startActivity(intent)
    }
}