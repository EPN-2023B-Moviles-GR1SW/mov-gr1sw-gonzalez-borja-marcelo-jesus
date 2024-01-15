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

lateinit var adaptadorCiudad: ArrayAdapter<BCiudad>
// para actualizar la lista
private lateinit var resultLauncher: ActivityResultLauncher<Intent>
class Actividad_Ciudad : AppCompatActivity() {
    var arreglo = BBaseDatosMemoria.arregloBCiudad
    var posicionItemSeleccionado = -1

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
        return when (item.itemId){
            R.id.mi_editar_ciudad->{
                val idPaisSeleccionado = intent.getIntExtra("ID_PAIS",-1)
                val ciudadesDelPais = arreglo.filter { it.idPaisCorresponde == idPaisSeleccionado }
                var idAEliminar = ciudadesDelPais[posicionItemSeleccionado].id
                var indxArreglo = encontrarIndicePorId(arreglo, idAEliminar)

                // Crear intent con la nueva ciudad
                val intent = Intent(this, Actualizar_Ciudad::class.java)
                intent.putExtra("ID_CIUDAD", indxArreglo)
                intent.putExtra("ID_PAIS", idPaisSeleccionado)
                // para actualizar la lista
                resultLauncher.launch(intent)

                mostrarSnackbar("${indxArreglo}")
                return true
            }
            R.id.mi_eliminar_ciudad ->{
                mostrarSnackbar("${posicionItemSeleccionado}")
                abrirDialogo()
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun mostrarSnackbar(texto: String){
        Snackbar.make(
            findViewById(R.id.lv_list_ciudad),
            texto,
            Snackbar.LENGTH_LONG
        ).show()
    }

    fun abrirDialogo() {
        val builder = AlertDialog.Builder(this)
        val idPaisSeleccionado = intent.getIntExtra("ID_PAIS",-1)
        val ciudadesDelPais = arreglo.filter { it.idPaisCorresponde == idPaisSeleccionado }
        var idAEliminar = ciudadesDelPais[posicionItemSeleccionado].id
        var indxArreglo = encontrarIndicePorId(arreglo, idAEliminar)

        builder.setTitle("Desea Eliminar "+ arreglo[indxArreglo])
        builder.setPositiveButton("Aceptar",
            DialogInterface.OnClickListener{ dialog, which ->
                arreglo.removeAt(indxArreglo)
                //
                actualizarLista()
                //
                mostrarSnackbar("Eliminar Aceptado")
            })
        builder.setNegativeButton("Cancelar",null)
        val dialogo = builder.create()
        dialogo.show()
    }

    fun encontrarIndicePorId(arregloCiudades: List<BCiudad>, idBuscado: Int): Int {
        for ((indice, ciudad) in arregloCiudades.withIndex()) {
            if (ciudad.id == idBuscado) {
                return indice // Devolver el índice si se encuentra el ID
            }
        }
        return -1 // Devolver -1 si no se encuentra el ID en el arreglo
    }
    fun actualizarLista() {
        val idPaisSeleccionado = intent.getIntExtra("ID_PAIS", -1)
        val ciudadesDelPais = arreglo.filter { it.idPaisCorresponde == idPaisSeleccionado }
        adaptadorCiudad = ArrayAdapter(this, android.R.layout.simple_list_item_1, ciudadesDelPais)
        val listView = findViewById<ListView>(R.id.lv_list_ciudad)
        listView.adapter = adaptadorCiudad
        adaptadorCiudad.notifyDataSetChanged()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actividad_ciudad)
        //
        val idPaisSeleccionado = intent.getIntExtra("ID_PAIS",-1)
        val ciudadesDelPais = arreglo.filter { it.idPaisCorresponde == idPaisSeleccionado }
        //
        val listView = findViewById<ListView>(R.id.lv_list_ciudad)
        adaptadorCiudad = ArrayAdapter(this, android.R.layout.simple_list_item_1, ciudadesDelPais)
        listView.adapter = adaptadorCiudad
        adaptadorCiudad.notifyDataSetChanged()

        val botonAnadirCiudad= findViewById<Button>(R.id.btn_anadir_ciudades)
        botonAnadirCiudad.setOnClickListener {
            anadirCiudad()

        }

        registerForContextMenu(listView)

        // para actualizar la lista
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // Reasignar el adaptador a la lista actualizada
                actualizarLista()
            }
        }

    }

    fun anadirCiudad(){
        val idPaisSeleccionado = intent.getIntExtra("ID_PAIS", -1)
        val nuevaCiudadId = arreglo[arreglo.count()-1].id+1

        // Crear intent con la nueva ciudad
        val intent = Intent(this, Crear_Ciudad::class.java)
        intent.putExtra("ID_CIUDAD", nuevaCiudadId)
        intent.putExtra("ID_PAIS", idPaisSeleccionado)
        // para actualizar la lista
        resultLauncher.launch(intent)
    }

}