package com.example.b2023_examen_mjgb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import com.google.android.material.snackbar.Snackbar

import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList


lateinit var adaptadorPais: ArrayAdapter<BPais>

class Actividad_Pais : AppCompatActivity() {
    var query: Query? = null
    val arreglo: ArrayList<BPais> = arrayListOf()
    var posicionItemSeleccionado = -1

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actividad_pais)

        // Configurando el list view
        val listView = findViewById<ListView>(R.id.lv_list_paises)
        adaptadorPais = ArrayAdapter(this, android.R.layout.simple_list_item_1, arreglo)
        listView.adapter = adaptadorPais
        adaptadorPais.notifyDataSetChanged()

        consultarPaises(adaptadorPais!!)

        // Botones
        val botonAnadirPais = findViewById<Button>(R.id.btn_anadir_paises)
        botonAnadirPais.setOnClickListener {
            anadirPais()
        }

        val botonActualizarCPais = findViewById<Button>(R.id.btn_actualizar_vista_pais)
        botonActualizarCPais.setOnClickListener {
            actualizarPais()
        }
        registerForContextMenu(listView)
    }

    fun actualizarPais() {
        adaptadorPais.notifyDataSetChanged()
        consultarPaises(adaptadorPais!!)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        // opciones del menu
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_paises, menu)
        //id del arraylistseleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSeleccionado = posicion
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editar_pais -> {
                irActividadConId(
                    Actualizar_Pais::class.java,
                    arreglo[posicionItemSeleccionado].id!!
                )
                return true
            }

            R.id.mi_eliminar_pais -> {
                eliminarPais(arreglo[posicionItemSeleccionado].id!!)
                arreglo.removeAt(posicionItemSeleccionado)
                adaptadorPais.notifyDataSetChanged()
                mostrarSnackbar("Eliminar Aceptado")
                return true
            }

            R.id.mi_ver_ciudades -> {
                val idPaisSeleccionado = arreglo[posicionItemSeleccionado].id!!
                irActividadConId(Actividad_Ciudad::class.java, idPaisSeleccionado)
                return true
            }

            else -> super.onContextItemSelected(item)
        }
    }


    fun consultarPaises(adaptador: ArrayAdapter<BPais>) {
        val db = Firebase.firestore
        val referencia = db.collection("paises")
        limpiarArreglo()
        adaptador.notifyDataSetChanged()
        referencia
            .get()
            .addOnSuccessListener {
                for (pais in it) {
                    pais.id
                    anadirAPais(pais)
                }
                adaptador.notifyDataSetChanged()
            }
            .addOnFailureListener {
            }
    }

    fun irActividadConId(clase: Class<*>, idPais: String) {
        val intent = Intent(this, clase)
        intent.putExtra("ID_PAIS", idPais)
        startActivity(intent)
    }

    fun irActividad(clase: Class<*>) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }

    fun mostrarSnackbar(texto: String) {
        Snackbar.make(
            findViewById(R.id.lv_list_paises),
            texto,
            Snackbar.LENGTH_LONG
        ).show()
    }

    fun eliminarPais(idPais: String) {
        val db = Firebase.firestore
        val referencia = db.collection("paises")
        val referenciaPais = db.collection("paises").document(idPais)
        val referenciaCiudad = db.collection("ciudades")
        //ELIMINAR CIUDADES
        referenciaPais
            .get()
            .addOnSuccessListener {
                val ciudades = it.data?.get("ciudades") as ArrayList<HashMap<String, Any>>
                Log.d("a", "eliminarPais: "+ciudades)
                if (ciudades != null) {
                    for (ciudad in ciudades) {
                        val ciudadObj = BCiudad(
                            ciudad["id"] as String,
                            ciudad["nombre"] as String,
                            (ciudad["poblacion"] as Long).toInt(),
                            ciudad["esCapital"] as Boolean,
                            ciudad["fechaFund"] as String
                        )
                        Log.d("a", "eliminarPais: "+ciudadObj.id)
                        referenciaCiudad
                            .document(ciudadObj.id!!)
                            .delete()
                            .addOnSuccessListener {}
                    }
                }
                // ELIMINAR
                referencia
                    .document(idPais)
                    .delete()
                    .addOnSuccessListener {
                        arreglo.removeIf { it.id == idPais }
                        adaptadorPais!!.notifyDataSetChanged()
                    }
                    .addOnFailureListener {
                    }
            }
        //ELIMINAR CIUDADES

    }

    fun anadirAPais(pais: QueryDocumentSnapshot) {
        val nuevoPais = BPais(
            pais.id,
            pais.data.get("nombre") as String?,
            pais.data.get("capital") as String?,
            (pais.data.get("poblacion") as? Long)?.toInt(),
            pais.data.get("tasaCrecimiento") as? Double?,
            pais.data.get("ciudades") as ArrayList<BCiudad>?,
        )
        arreglo.add(nuevoPais)
    }

    fun anadirPais() {
        irActividad(CrearPais::class.java)
        adaptadorPais.notifyDataSetChanged()
    }

    fun limpiarArreglo() {
        arreglo.clear()
    }

}