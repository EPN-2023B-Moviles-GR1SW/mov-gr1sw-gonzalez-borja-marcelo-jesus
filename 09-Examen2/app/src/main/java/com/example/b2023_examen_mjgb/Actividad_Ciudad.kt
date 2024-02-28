package com.example.b2023_examen_mjgb

import android.app.Activity
import android.content.DialogInterface
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
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar

import android.annotation.SuppressLint
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

lateinit var adaptadorCiudad: ArrayAdapter<BCiudad>

// para actualizar la lista
private lateinit var resultLauncher: ActivityResultLauncher<Intent>

class Actividad_Ciudad : AppCompatActivity() {
    val arregloCiudades: ArrayList<BCiudad> = arrayListOf()
    var posicionItemSeleccionado = -1
    var idPaisSeleccionado: String? = null


    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actividad_ciudad)

        idPaisSeleccionado = intent.getStringExtra("ID_PAIS")

        val listView = findViewById<ListView>(R.id.lv_list_ciudad)
        adaptadorCiudad = ArrayAdapter(this, android.R.layout.simple_list_item_1, arregloCiudades)
        listView.adapter = adaptadorCiudad
        adaptadorCiudad.notifyDataSetChanged()
        consultarCiudadesDelPais(idPaisSeleccionado!!)

        val botonAnadirCiudad = findViewById<Button>(R.id.btn_anadir_ciudades)
        botonAnadirCiudad.setOnClickListener {
            anadirCiudad(idPaisSeleccionado!!)
        }
        registerForContextMenu(listView)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        // opciones del menu
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_ciudades, menu)
        //id del arraylistseleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSeleccionado = posicion
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editar_ciudad -> {

                // Crear intent con la nueva ciudad
                val intent = Intent(this, ActualizarCiudad::class.java)
                intent.putExtra("ID_CIUDAD", arregloCiudades[posicionItemSeleccionado].id)
                intent.putExtra("ID_PAIS", idPaisSeleccionado)
                startActivity(intent)
                finish()
                return true
            }

            R.id.mi_eliminar_ciudad -> {
                eliminarCiudad(arregloCiudades[posicionItemSeleccionado].id!!, idPaisSeleccionado!!)
                adaptadorCiudad.notifyDataSetChanged()
                mostrarSnackbar("Eliminar Aceptado")
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun eliminarCiudad(idCiudad: String, idPais: String) {
        val db = Firebase.firestore
        val referenciaPais = db.collection("paises").document(idPais)
        val referenciaCiudad = db.collection("ciudades")

        for (ciudad in arregloCiudades) {
            if (ciudad.id == idCiudad) {
                arregloCiudades.remove(ciudad)
                break
            }
        }
        adaptadorCiudad.notifyDataSetChanged()
        referenciaCiudad
            .document(idCiudad)
            .delete()
            .addOnSuccessListener {
                referenciaPais
                    .update("ciudades", arregloCiudades)
                    .addOnSuccessListener { adaptadorCiudad.notifyDataSetChanged() }
                    .addOnFailureListener { }
            }
    }

    fun mostrarSnackbar(texto: String) {
        Snackbar.make(
            findViewById(R.id.lv_list_ciudad),
            texto,
            Snackbar.LENGTH_LONG
        ).show()
    }

    fun anadirCiudad(idPais: String) {
        // Crear intent con la nueva ciudad
        val intent = Intent(this, CrearCiudad::class.java)
        intent.putExtra("ID_PAIS", idPais)
        startActivity(intent)
    }

    fun consultarCiudadesDelPais(idPais: String) {
        val db = Firebase.firestore
        val referencia = db.collection("paises").document(idPais)
        limpiarArreglo()
        adaptadorCiudad.notifyDataSetChanged()
        referencia
            .get()
            .addOnSuccessListener {
                val ciudades = it.data?.get("ciudades") as ArrayList<HashMap<String, Any>>
                if (ciudades != null) {
                    for (ciudad in ciudades) {
                        val ciudadObj = BCiudad(
                            ciudad["id"] as String,
                            ciudad["nombre"] as String,
                            (ciudad["poblacion"] as Long).toInt(),
                            ciudad["esCapital"] as Boolean,
                            ciudad["fechaFund"] as String
                        )
                        arregloCiudades.add(ciudadObj)
                    }
                }
                adaptadorCiudad.notifyDataSetChanged()
            }
            .addOnFailureListener { }
    }

    fun limpiarArreglo() {
        arregloCiudades.clear()
    }

}