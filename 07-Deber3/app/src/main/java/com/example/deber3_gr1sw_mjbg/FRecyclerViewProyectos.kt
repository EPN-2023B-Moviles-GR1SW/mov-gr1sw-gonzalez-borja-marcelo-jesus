package com.example.deber3_gr1sw_mjbg

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class FRecyclerViewProyectos : AppCompatActivity() {
    var totalTareas = 0
    val arreglo = BBaseDatosMemoria.arregloBProyectos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frecycler_view_proyectos)
        inicializarRecyclerView()
        val totalTareasTextView = findViewById<TextView>(R.id.tv_total_proyectos)
        totalTareasTextView.text = arreglo.size.toString()+"/5"

        val botonAgregar = findViewById<ImageButton>(R.id.img_add_proyectos)
        botonAgregar.setOnClickListener {
            crearProyecto()
        }


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

    fun crearProyecto(){
        if (arreglo.size<5){
            val ultimoId = arreglo[arreglo.count()-1].id+1
            val id = ultimoId
            val nombre = "Proyecto $id"
            val descripcion = "Información del $nombre"
            val color = Color.rgb(255,255,0)
            val nuevoProyecto = BProyectos(id, nombre, descripcion, color)
            BBaseDatosMemoria.arregloBProyectos.add(nuevoProyecto)
            aumentarTotalProyectos()
            val recyclerView = findViewById<RecyclerView>(R.id.rv_proyectos)
            val adaptador = recyclerView.adapter as FRecyclerAdaptadorProyectos
            adaptador.notifyDataSetChanged()
        }else   {

        }

    }

    fun aumentarTotalProyectos(){
        val totalTareasTextView = findViewById<TextView>(R.id.tv_total_proyectos)
        totalTareasTextView.text = arreglo.size.toString()+"/5"
    }
    fun irActividad(clase: Class<*>){
        val intent = Intent(this,clase)
        startActivity(intent)
    }
}