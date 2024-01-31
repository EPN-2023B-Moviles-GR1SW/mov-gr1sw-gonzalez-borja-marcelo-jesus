package com.example.b2023_examen_mjgb

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
class ESqliteHelperCiudad(contexto: Context?):
    SQLiteOpenHelper(contexto, "dbCiudad", null,1){
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaCIudad =
            """
                CREATE TABLE CIUDAD( 
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(50),
                idPaisCorresponde INTEGER,
                poblacion INTEGER,
                esCapital BOOLEAN CHECK(esCapital IN(0,1)),
                fechaFundacion VARCHAR(15)
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaCIudad)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun crearCiudad(nombre: String, idPaisCorresponde: Int,  poblacion: Int, esCapital: Boolean,
                    fechaFundacion: String):
            Boolean {
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("idPaisCorresponde", idPaisCorresponde)
        valoresAGuardar.put("poblacion", poblacion)
        valoresAGuardar.put("esCapital", esCapital)
        valoresAGuardar.put("fechaFundacion", fechaFundacion)
        val resultadoGuardar = basedatosEscritura
            .insert(
                "CIUDAD", // Nombre tabla
                null,
                valoresAGuardar // valores
            )
        basedatosEscritura.close()
        return if (resultadoGuardar.toInt() == -1) false else true
    }

    fun eliminarCiudadFormulario(id: Int): Boolean{
        val conexionEscritura = writableDatabase
        // where ID = ?
        val parametrosConsultaDelete = arrayOf( id.toString())
        val resultadoEliminacion = conexionEscritura
            // Delete from id = 1 ===> [ 1 ]
            .delete(
                "CIUDAD", // Nombre tabla
                "id=?", // Consulta Where
                parametrosConsultaDelete
            )
        conexionEscritura.close()
        return if (resultadoEliminacion == -1) false else true
    }

    fun actualizarCiudadFormulario(nombre: String, idPaisCorresponde: Int,  poblacion: Int, esCapital: Boolean,
                                   fechaFundacion: String, id: Int, ):Boolean{
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("idPaisCorresponde", idPaisCorresponde)
        valoresAActualizar.put("poblacion", poblacion)
        valoresAActualizar.put("esCapital", esCapital)
        valoresAActualizar.put("fechaFundacion", fechaFundacion)
        // where ID = ?
        val parametrosConsultaActualizar = arrayOf( id.toString())
        val resultadoActualizacion = conexionEscritura
            .update(
                "CIUDAD", // Nombre tabla
                valoresAActualizar, // Valores
                "id=?", // Consulta Where
                parametrosConsultaActualizar
            )
        conexionEscritura.close()
        return if(resultadoActualizacion == -1) false else true
    }

    fun consultarCiudadPorID(id: Int): BCiudad {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM CIUDAD WHERE ID = ?
        """.trimIndent()
        val parametrosConsultaLectura = arrayOf(id.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura, // Consulta
            parametrosConsultaLectura, // Parametros
        )
        // logica busqueda
        val existeUsuario = resultadoConsultaLectura.moveToFirst()
        val usuarioEncontrado = BCiudad (0,"",0,0,true,"")
        // val arreglo = arrayListOf<BEntrenador>() ARREGLO
        if(existeUsuario) {
            do {
                val id = resultadoConsultaLectura.getInt(0) // Indice
                val nombre = resultadoConsultaLectura.getString(1)
                val idPaisCorresponde = resultadoConsultaLectura.getString(2)
                val poblacion = resultadoConsultaLectura.getString(3)
                val esCapital = resultadoConsultaLectura.getString(4)
                val fechaFund = resultadoConsultaLectura.getString(5)

                if(id != null){
                    // val usuarioEncontrado = BEntrenador (0, "", "") ARREGLO
                    // llenar el arreglo con un nuevo BEntrenador
                    usuarioEncontrado.id = id
                    usuarioEncontrado.nombre = nombre
                    usuarioEncontrado.idPaisCorresponde = idPaisCorresponde.toInt()
                    usuarioEncontrado.poblacion = poblacion.toInt()
                    usuarioEncontrado.esCapital = esCapital.toBoolean()
                    usuarioEncontrado.fechaFund = fechaFund
                    // arreglo.add(usuarioEncontrado) ARREGLO
                }
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return usuarioEncontrado
    }

    fun consultarCiudades(): List<BCiudad> {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
        SELECT * FROM CIUDAD
    """.trimIndent()
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaLectura, null)
        val listaCiudades = mutableListOf<BCiudad>()

        if (resultadoConsultaLectura.moveToFirst()) {
            do {
                val id = resultadoConsultaLectura.getInt(0) // Indice
                val nombre = resultadoConsultaLectura.getString(1)
                val idPaisCorresponde = resultadoConsultaLectura.getString(2)
                val poblacion = resultadoConsultaLectura.getString(3)
                val esCapital = resultadoConsultaLectura.getString(4)
                val fechaFund = resultadoConsultaLectura.getString(5)

                val ciudad = BCiudad(id, nombre, idPaisCorresponde.toInt(), poblacion.toInt(), esCapital.toBoolean(),fechaFund )
                listaCiudades.add(ciudad)

            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return listaCiudades
    }
}