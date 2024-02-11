package com.example.deber3_gr1sw_mjbg

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FRecyclerViewTareas : AppCompatActivity() {
    val arreglo = BBaseDatosMemoria.arregloBTareas

    var totalTareas = arreglo.size
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frecycler_view_tareas)
        inicializarRecyclerView()
        val totalTareasTextView = findViewById<TextView>(R.id.tv_total_tareas_proyecto)
        totalTareasTextView.text = arreglo.size.toString()

        val botonRegresar = findViewById<ImageButton>(R.id.img_btn_back)
        botonRegresar.setOnClickListener {
            irActividad(FRecyclerViewProyectos::class.java)
        }

        val botonAgregar = findViewById<ImageButton>(R.id.img_btn_add)
        botonAgregar.setOnClickListener {
            aumentarTotalTareas()
            crearTarea()
        }
    }

    fun irActividad(clase: Class<*>){
        val intent = Intent(this,clase)
        startActivity(intent)
    }
    fun inicializarRecyclerView(){
        val recyclerView = findViewById<RecyclerView>(R.id.rv_tareas)
        val adaptador = FRecyclerAdaptadorTareas(
            this, // Contexto
            BBaseDatosMemoria.arregloBTareas, // Arreglo datos
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
        totalTareas += 1
        val totalTareasTextView = findViewById<TextView>(R.id.tv_total_tareas_proyecto)
        totalTareasTextView.text = totalTareas.toString()
    }
    fun crearTarea(){
        val ultimoId = arreglo[arreglo.count()-1].id+1
        val id = ultimoId
        val nombre = "Tarea $id"
        val descripcion = "Información de la $nombre"
        val fecha = "hoy"
        val nuevaTarea = BTareas(id, nombre, descripcion, fecha)
        BBaseDatosMemoria.arregloBTareas.add(nuevaTarea)
        val recyclerView = findViewById<RecyclerView>(R.id.rv_tareas)
        val adaptador = recyclerView.adapter as FRecyclerAdaptadorTareas
        adaptador.notifyDataSetChanged()
    }
    fun disminuirTotalTareas (){
        totalTareas -= 1
        val totalTareasTextView = findViewById<TextView>(R.id.tv_total_tareas_proyecto)
        totalTareasTextView.text = totalTareas.toString()
    }
}