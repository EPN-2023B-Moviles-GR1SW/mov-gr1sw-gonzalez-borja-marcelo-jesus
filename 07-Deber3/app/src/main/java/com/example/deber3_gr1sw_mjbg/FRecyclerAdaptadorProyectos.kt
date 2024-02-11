package com.example.deber3_gr1sw_mjbg

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FRecyclerAdaptadorProyectos (
    private val contexto: FRecyclerViewProyectos,
    private val lista: ArrayList<BProyectos>,
    private val recyclerView: RecyclerView
): RecyclerView.Adapter<FRecyclerAdaptadorProyectos.MyViewHolder>(){
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(
        view
    ) {
        val nombreTextView: TextView
        val tareasPTextView: TextView
        val imgNImageView: ImageView
        val accionButton: Button
        val accionTareaButton: Button
        var numeroTareas = 0

        init {
            imgNImageView = view.findViewById(R.id.imgv_num)
            nombreTextView = view.findViewById(R.id.tv_nombre_proyecto)
            tareasPTextView = view.findViewById(R.id.tv_numero_tareas)
            accionTareaButton = view.findViewById(R.id.btn_ir_tareas)
            accionButton = view.findViewById(R.id.btn_agregar_tarea_proyecto)
            accionButton.setOnClickListener { anadirTarea() }
            accionTareaButton.setOnClickListener { irActividad(FRecyclerViewTareas::class.java) }
        }
        fun irActividad(clase: Class<*>){
            val intent = Intent(contexto,clase)
            contexto.startActivity(intent)
        }
        fun anadirTarea(){
            numeroTareas = numeroTareas + 1
            tareasPTextView.text = numeroTareas.toString()
            contexto.aumentarTotalTareas()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            FRecyclerAdaptadorProyectos.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_vew_proyectos, parent,
            false
        )
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return this.lista.size
    }

    override fun onBindViewHolder(holder: FRecyclerAdaptadorProyectos.MyViewHolder, position: Int) {
        val proyectoActual = this.lista [position]
        holder.nombreTextView.text = proyectoActual.nombre
        holder.tareasPTextView.text = "0"
        holder.imgNImageView.setColorFilter(proyectoActual.color)
        //holder.accionButton.text = "ID: ${proyectoActual.id}" + "Nombre:${proyectoActual.nombre}"
    }
}