import java.io.*
import java.text.SimpleDateFormat
import java.util.Date

var idFinalPais = 1
var idFinalCiudad = 1
data class Pais(
    var id: Int,
    var nombre: String,
    var poblacion: Int,
    var capital: String,
    var tasaCrecimiento: Double
) : Serializable

data class Ciudad(
    var id: Int,
    var nombre: String,
    var poblacion: Int,
    var fechaFundacion: Date,
    var esCapital: Boolean,
    var paisID: Int
) : Serializable

class GestorCRUD{
    private val paises = mutableListOf<Pais>()
    private val ciudades = mutableListOf<Ciudad>()

    // CRUD PAIS
    fun crearPais(pais: Pais){
        paises.add(pais)
        guardarDatos()
    }

    fun obtenerPais(id: Int): Pais? {
        return paises.find { it.id == id }
    }

    fun obtenerPaises(): List<Pais> {
        return paises.toList()
    }

    fun actualizarPais(pais: Pais) {
        val index =paises.indexOfFirst { it.id == pais.id }
        if (index != -1){
            paises[index] = pais
            guardarDatos()
        }
    }

    fun eliminarPais(id: Int){
        paises.removeIf { it.id == id }
        ciudades.removeIf { it.paisID == id}
        guardarDatos()
    }

    // CRUD CIUDAD
    fun crearCiudad(ciudad: Ciudad){
        ciudades.add(ciudad)
        guardarDatos()
    }

    fun obtenerCiudad(id: Int): Ciudad? {
        return ciudades.find { it.id == id }
    }

    fun obtenerCiudades(): List<Ciudad> {
        return ciudades.toList()
    }

    fun obtenerCiudadesPorPais(idPais: Int): List<Ciudad> {
        return ciudades.filter { it.paisID == idPais }
    }

    fun actualizarCiudad(ciudad: Ciudad) {
        val index =ciudades.indexOfFirst { it.id == ciudad.id }
        if (index != -1){
            ciudades[index] = ciudad
            guardarDatos()
        }
    }

    fun eliminarCiudad(id: Int){
        ciudades.removeIf { it.id == id }
        guardarDatos()
    }

    // Método para guardar datos en archivos
    private fun guardarDatos() {
        ObjectOutputStream(FileOutputStream("paises.dat")).use { it.writeObject(paises) }
        ObjectOutputStream(FileOutputStream("ciudades.dat")).use { it.writeObject(ciudades) }
    }

    // Método para cargar datos desde archivos
    fun cargarDatos() {
        if (File("paises.dat").exists()) {
            ObjectInputStream(FileInputStream("paises.dat")).use { paises.addAll(it.readObject() as List<Pais>) }
            idFinalPais = paises.maxByOrNull { it.id }?.id ?: 0
        }
        if (File("ciudades.dat").exists()) {
            ObjectInputStream(FileInputStream("ciudades.dat")).use { ciudades.addAll(it.readObject() as List<Ciudad>) }
            idFinalCiudad = ciudades.maxByOrNull { it.id }?.id ?: 0
        }
    }
}
fun main() {
    val gestorCRUD = GestorCRUD()
    gestorCRUD.cargarDatos()

    var opcion: Int

    do {
        println("========== Menú ==========")
        println("1. Crear País")
        println("2. Crear Ciudad")
        println("3. Actualizar País")
        println("4. Actualizar Ciudad")
        println("5. Eliminar País")
        println("6. Eliminar Ciudad")
        println("7. Mostrar Países")
        println("8. Mostrar un Pais")
        println("9. Mostrar Ciudades")
        println("10. Mostrar Ciudades por País")
        println("11. Mostrar una Ciudad")
        println("0. Salir")
        print("Ingrese su opción: ")

        opcion = readlnOrNull()?.toIntOrNull() ?: -1

        when (opcion) {
            1 -> {
                val pais = leerDatosPais()
                gestorCRUD.crearPais(pais)
            }
            2 -> {
                val ciudad = leerDatosCiudad(gestorCRUD.obtenerPaises())
                gestorCRUD.crearCiudad(ciudad)
            }
            3 -> {
                println("Ingrese el ID del país que desea actualizar:")
                val idPaisActualizar = readLine()?.toIntOrNull()
                if (idPaisActualizar != null) {
                    val paisExistente = gestorCRUD.obtenerPais(idPaisActualizar)
                    if (paisExistente != null) {
                        val paisActualizado = leerDatosPais()
                        paisActualizado.id = idPaisActualizar
                        gestorCRUD.actualizarPais(paisActualizado)
                        println("País actualizado correctamente.")
                    } else {
                        println("No se encontró un país con el ID proporcionado.")
                    }
                } else {
                    println("ID inválido. Operación cancelada.")
                }
            }
            4 -> {
                println("Ingrese el ID de la ciudad que desea actualizar:")
                val idCiudadActualizar = readLine()?.toIntOrNull()
                if (idCiudadActualizar != null) {
                    val ciudadExistente = gestorCRUD.obtenerCiudad(idCiudadActualizar)
                    if (ciudadExistente != null) {
                        val ciudadActualizada = leerDatosCiudad(gestorCRUD.obtenerPaises())
                        ciudadActualizada.id = idCiudadActualizar
                        gestorCRUD.actualizarCiudad(ciudadActualizada)
                        println("Ciudad actualizada correctamente.")
                    } else {
                        println("No se encontró una ciudad con el ID proporcionado.")
                    }
                } else {
                    println("ID inválido. Operación cancelada.")
                }
            }
            5 -> {
                println("Ingrese el ID del país a eliminar:")
                val idPaisEliminar = readlnOrNull()?.toIntOrNull()
                if (idPaisEliminar != null) {
                    gestorCRUD.eliminarPais(idPaisEliminar)
                } else {
                    println("ID inválido. Operación cancelada.")
                }
            }
            6 -> {
                println("Ingrese el ID de la ciudad a eliminar:")
                val idCiudadEliminar = readlnOrNull()?.toIntOrNull()
                if (idCiudadEliminar != null) {
                    gestorCRUD.eliminarCiudad(idCiudadEliminar)
                } else {
                    println("ID inválido. Operación cancelada.")
                }
            }
            7 -> {
                println("Lista de Países:")
                gestorCRUD.obtenerPaises().forEach{ println(it) }
            }
            8 -> {
                print("Ingrese el ID del país:")
                val idPaisMostrar = readlnOrNull()?.toIntOrNull()
                if (idPaisMostrar != null) {
                    println(gestorCRUD.obtenerPais(idPaisMostrar))
                }else{
                    println("ID inválido")
                }
            }
            9 -> {
                println("Lista de Ciudades:")
                gestorCRUD.obtenerCiudades().forEach{ println(it) }
            }
            10 -> {
                print("Ingrese el ID del país para ver sus ciudades:")
                val idPaisMostrar = readlnOrNull()?.toIntOrNull()
                if (idPaisMostrar != null) {
                    val ciudades = gestorCRUD.obtenerCiudadesPorPais(idPaisMostrar)
                    val nombrePais = gestorCRUD.obtenerPais(idPaisMostrar)?.nombre
                    println("Ciudades en el país $idPaisMostrar ($nombrePais):")
                    ciudades.forEach { println(it) }
                } else {
                    println("ID inválido. Operación cancelada.")
                }
            }
            11 -> {
                print("Ingrese el ID de la Ciudad:")
                val idCiudadMostrar = readlnOrNull()?.toIntOrNull()
                if (idCiudadMostrar != null) {
                    println(gestorCRUD.obtenerCiudad(idCiudadMostrar))
                }else{
                    println("ID inválido")
                }
            }
            0 -> println("Saliendo del programa.")
            else -> println("Opción no válida. Inténtelo de nuevo.")
        }
    } while (opcion != 0)
}

fun leerDatosPais(): Pais {
    println("Ingrese el nombre del país:")
    val nombre = readlnOrNull() ?: ""

    println("Ingrese la población del país:")
    val poblacion = readlnOrNull()?.toIntOrNull() ?: 0

    println("Ingrese la capital del país:")
    val capital = readlnOrNull() ?: ""

    println("Ingrese la tasa de crecimiento del país:")
    val tasaCrecimiento = readlnOrNull()?.toDoubleOrNull() ?: 0.0

    idFinalPais++
    val nuevoPais = Pais(idFinalPais, nombre, poblacion, capital, tasaCrecimiento)

    return nuevoPais
}

fun leerDatosCiudad(paises: List<Pais>): Ciudad {
    println("Ingrese el nombre de la ciudad:")
    val nombre = readlnOrNull() ?: ""

    println("Ingrese la población de la ciudad:")
    val poblacion = readlnOrNull()?.toIntOrNull() ?: 0

    println("Ingrese la fecha de fundación de la ciudad (formato dd-MM-yyyy):")
    val fechaStr = readlnOrNull() ?: ""
    val formatoFecha = SimpleDateFormat("dd-MM-yyyy")
    val fechaFundacion: Date = fechaStr?.takeIf { it.isNotEmpty() }?.let {
        formatoFecha.parse(it)
    } ?: Date(0)

    println("¿Es la ciudad la capital? (true/false):")
    val esCapital = readlnOrNull()?.toBoolean() ?: false

    println("Seleccione el país al que pertenece la ciudad:")
    paises.forEachIndexed { index, pais ->
        println("$index. ${pais.nombre}")
    }
    val paisIndex = readlnOrNull()?.toIntOrNull() ?: 0
    val paisId = if (paisIndex in paises.indices) paises[paisIndex].id else 0

    idFinalCiudad++
    val nuevaCiudad = Ciudad(idFinalCiudad, nombre, poblacion, fechaFundacion, esCapital, paisId)

    return nuevaCiudad
}