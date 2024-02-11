package com.example.deber3_gr1sw_mjbg

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FRecyclerViewProyectos : AppCompatActivity() {
    var totalTareas = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frecycler_view_proyectos)
        inicializarRecyclerView()
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
}