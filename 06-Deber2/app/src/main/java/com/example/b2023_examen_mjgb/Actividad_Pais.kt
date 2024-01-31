package com.example.b2023_examen_mjgb

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
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar

lateinit var adaptadorPais: ArrayAdapter<BPais>
class Actividad_Pais : AppCompatActivity() {
    val arreglo = BBaseDatosMemoria.arregloBPais
    val arregloSQL = EBaseDeDatos.tablaPais!!.consultarPaises()
    var posicionItemSeleccionado = -1

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
        return when (item.itemId){
            R.id.mi_editar_pais ->{
                irActividadConId(Actualizar_Pais::class.java, posicionItemSeleccionado)
                return true
            }
            R.id.mi_eliminar_pais ->{
                abrirDialogo()
                return true
            }
            R.id.mi_ver_ciudades ->{
                val idPaisSeleccionado = arregloSQL[posicionItemSeleccionado].id
                irActividadConId(Actividad_Ciudad::class.java, idPaisSeleccionado)
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun irActividadConId(clase: Class<*>, idPais: Int){
        val intent = Intent(this,clase)
        intent.putExtra("ID_PAIS", idPais)
        startActivity(intent)
    }
    fun mostrarSnackbar(texto: String){
        Snackbar.make(
            findViewById(R.id.lv_list_paises),
            texto,
            Snackbar.LENGTH_LONG
        ).show()
    }

    fun abrirDialogo() {
        val builder = AlertDialog.Builder(this)
        val idPais = arregloSQL[posicionItemSeleccionado].id
        val pais = EBaseDeDatos.tablaPais!!.consultarPaisPorID(idPais)
        builder.setTitle("Desea Eliminar " + pais.nombre)
        builder.setPositiveButton("Aceptar",
            DialogInterface.OnClickListener{dialog, which ->
                val respuesta = EBaseDeDatos.tablaPais!!.eliminarPaisFormulario(idPais)
                if (respuesta){
                    mostrarSnackbar("Pais Eliminado")
                    adaptadorPais.notifyDataSetChanged()
                }

            })
        builder.setNegativeButton("Cancelar",null)
        val dialogo = builder.create()
        dialogo.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actividad_pais)

        val listView = findViewById<ListView>(R.id.lv_list_paises)
        adaptadorPais = ArrayAdapter(this, android.R.layout.simple_list_item_1, arregloSQL)
        listView.adapter = adaptadorPais
        adaptadorPais.notifyDataSetChanged()

        val botonAnadirPais = findViewById<Button>(R.id.btn_anadir_paises)
        botonAnadirPais.setOnClickListener {
            anadirPais()
        }
        registerForContextMenu(listView)
    }

    fun anadirPais(){
        val ultimoId = arregloSQL[arregloSQL.count()-1].id+1
        irActividadConId(CrearPais::class.java, ultimoId)
    }
}