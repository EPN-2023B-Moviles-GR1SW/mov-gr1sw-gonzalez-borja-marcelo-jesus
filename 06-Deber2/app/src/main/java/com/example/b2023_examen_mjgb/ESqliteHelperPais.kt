package com.example.b2023_examen_mjgb

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
class ESqliteHelperPais(contexto: Context?) :
    SQLiteOpenHelper(contexto, "dbPais", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaPais =
            """
                CREATE TABLE PAIS( 
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(50),
                capital VARCHAR(50),
                poblacion INTEGER,
                tasaCrecimiento DOUBLE
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaPais)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun crearPais(nombre: String, capital: String, poblacion: Int, tasaCrecimiento: Double):
            Boolean {
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("capital", capital)
        valoresAGuardar.put("poblacion", poblacion)
        valoresAGuardar.put("tasaCrecimiento", tasaCrecimiento)
        val resultadoGuardar = basedatosEscritura
            .insert(
                "PAIS", // Nombre tabla
                null,
                valoresAGuardar // valores
            )
        basedatosEscritura.close()
        return if (resultadoGuardar.toInt() == -1) false else true
    }

    fun eliminarPaisFormulario(id: Int): Boolean{
        val conexionEscritura = writableDatabase
        // where ID = ?
        val parametrosConsultaDelete = arrayOf( id.toString())
        val resultadoEliminacion = conexionEscritura
            // Delete from id = 1 ===> [ 1 ]
            .delete(
                "PAIS", // Nombre tabla
                "id=?", // Consulta Where
                parametrosConsultaDelete
            )
        conexionEscritura.close()
        return if (resultadoEliminacion == -1) false else true
    }

    fun actualizarPaisFormulario(nombre: String, capital: String, poblacion: Int,
                                 tasaCrecimiento: Double, id: Int, ):Boolean{
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("capital", capital)
        valoresAActualizar.put("poblacion", poblacion)
        valoresAActualizar.put("tasaCrecimiento", tasaCrecimiento)
        // where ID = ?
        val parametrosConsultaActualizar = arrayOf( id.toString())
        val resultadoActualizacion = conexionEscritura
            .update(
                "PAIS", // Nombre tabla
                valoresAActualizar, // Valores
                "id=?", // Consulta Where
                parametrosConsultaActualizar
            )
        conexionEscritura.close()
        return if(resultadoActualizacion == -1) false else true
    }

    fun consultarPaisPorID(id: Int): BPais {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM PAIS WHERE ID = ?
        """.trimIndent()
        val parametrosConsultaLectura = arrayOf(id.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura, // Consulta
            parametrosConsultaLectura, // Parametros
        )
        // logica busqueda
        val existeUsuario = resultadoConsultaLectura.moveToFirst()
        val usuarioEncontrado = BPais (0, "", "", 0, 0.0)
        // val arreglo = arrayListOf<BEntrenador>() ARREGLO
        if(existeUsuario) {
            do {
                val id = resultadoConsultaLectura.getInt(0) // Indice
                val nombre = resultadoConsultaLectura.getString(1)
                val capital = resultadoConsultaLectura.getString(2)
                val poblacion = resultadoConsultaLectura.getString(3)
                val tasaCrecimiento = resultadoConsultaLectura.getString(4)
                if(id != null){
                    // val usuarioEncontrado = BEntrenador (0, "", "") ARREGLO
                    // llenar el arreglo con un nuevo BEntrenador
                    usuarioEncontrado.id = id
                    usuarioEncontrado.nombre = nombre
                    usuarioEncontrado.capital = capital
                    usuarioEncontrado.poblacion = poblacion.toInt()
                    usuarioEncontrado.tasaCrecimiento = tasaCrecimiento.toDouble()
                    // arreglo.add(usuarioEncontrado) ARREGLO
                }
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return usuarioEncontrado
    }

    fun consultarPaises(): List<BPais> {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
        SELECT * FROM PAIS
    """.trimIndent()
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaLectura, null)
        val listaPaises = mutableListOf<BPais>()

        if (resultadoConsultaLectura.moveToFirst()) {
            do {
                val id = resultadoConsultaLectura.getInt(0)
                val nombre = resultadoConsultaLectura.getString(1)
                val capital = resultadoConsultaLectura.getString(2)
                val poblacion = resultadoConsultaLectura.getString(3)
                val tasaCrecimiento = resultadoConsultaLectura.getString(4)

                val pais = BPais(id, nombre, capital, poblacion.toInt(), tasaCrecimiento.toDouble())
                listaPaises.add(pais)

            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return listaPaises
    }

    fun eliminarTodosPais(): Boolean {
        val conexionEscritura = writableDatabase
        val resultadoEliminacion = conexionEscritura.delete("PAIS", null, null)
        conexionEscritura.close()
        return resultadoEliminacion != -1
    }
}
