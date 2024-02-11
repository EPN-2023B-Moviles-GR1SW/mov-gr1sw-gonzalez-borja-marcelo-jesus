package com.example.deber3_gr1sw_mjbg

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FRecyclerAdaptadorTareas (
    private val contexto: FRecyclerViewTareas,
    private val lista: ArrayList<BTareas>,
    private val recyclerView: RecyclerView
): RecyclerView.Adapter<FRecyclerAdaptadorTareas.MyViewHolder>(){
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(
        view
    ) {
        val nombreTextView: TextView
        val descripcionTextView: TextView
        val fechaView: TextView
        val chkTareaButton: CheckBox

        init {
            nombreTextView = view.findViewById(R.id.tv_nombre_tarea)
            descripcionTextView = view.findViewById(R.id.tv_descripcion_tarea)
            fechaView = view.findViewById(R.id.tv_fecha_tarea)
            chkTareaButton = view.findViewById(R.id.chk_tarea)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            FRecyclerAdaptadorTareas.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_view_tareas, parent,
            false
        )
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return this.lista.size
    }

    override fun onBindViewHolder(holder: FRecyclerAdaptadorTareas.MyViewHolder, position: Int) {
        val tareaActual = this.lista [position]
        holder.nombreTextView.text = tareaActual.nombre
        holder.descripcionTextView.text = tareaActual.descripcion
        holder.fechaView.text = tareaActual.fecha
        holder.chkTareaButton.setOnCheckedChangeListener{_, isChecked ->
            if (isChecked) {
                contexto.disminuirTotalTareas()
            } else {
                contexto.aumentarTotalTareas()
            }
        }
    }
}