import java.util.ArrayList
import java.util.Date



fun main() {
    println("Hello World!")

    // Tipos de variables
    // Inmutables (no se reasignan)
    val inmutable: String = "Marcelo";

    //Mutable (re asignar)
    var mutable: String = "Jesus";

    // val > var
    // Duck Typing
    var ejemploVariable = "Jesus Gonzalez"
    val edadEjemplo:Int = 12
    ejemploVariable.trim()

    // Variable primitiva
    val nombreProfesor  = "Jesus Gonzalez"
    val sueldo: Double = 1.2
    val estadoCivil: Char = 'C'
    val mayorEdad: Boolean = true

    // Clases Java
    val fechaNacimiento: Date = Date()

    // Switch
    val estadoCivilWhen = "C"
    when (estadoCivilWhen){
        ("C") -> {
            println("Casado")
        }
        "S" -> {
            println("Soltero")
        }
        else -> {
            println("No sabemos")
        }
    }
    val coqueteo = if (estadoCivilWhen == "S") "Si" else "NO"

    calcularSueldo(10.00)
    calcularSueldo(10.00, 15.00)
    calcularSueldo(10.00, 12.00, 20.00)
    calcularSueldo(sueldo = 10.00, tasa = 12.00, bonoEspecial = 20.00) // Parametros nombrados
    calcularSueldo(10.00, bonoEspecial = 20.00) // Named Parameters
    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 14.00) // Parametros nombrados

    val sumaUno = Suma(1,1)
    val sumaDos = Suma(null,1)
    val sumaTres = Suma(1,null)
    val sumaCuatro = Suma(null, null)
    sumaUno.sumar()
    sumaDos.sumar()
    sumaTres.sumar()
    sumaCuatro.sumar()
    println(Suma.pi)
    println(Suma.elevarAlCuadrado(2))
    println(Suma.historialSumas)


    // Tipos de Arreglos
    // Arreglo Estatico
    val arregloEstatico: Array<Int> = arrayOf(1,2,3)
    println(arregloEstatico)

    // Arreglo Dinamico
    val arregloDinamico: ArrayList<Int> = arrayListOf(
        1, 2, 3, 4, 5, 6, 7, 8, 9, 10
    )
    println(arregloDinamico)
    arregloDinamico.add(11)
    arregloDinamico.add(12)
    println(arregloDinamico)

    // For Each -> Unit
    // Iterar un arreglo
    val respuestaForEach: Unit = arregloDinamico.forEach {
        valorActual: Int -> println("Valor Actual: ${valorActual}")
    }
    // it -> elemento iterado
    arregloDinamico.forEach{println(it)}

    arregloEstatico.forEachIndexed { indice: Int, valorActual ->
        println("Valor ${valorActual} Indice: ${indice}")
    }
    println(respuestaForEach)

    // Operadores

    // MAP -> Muta al arreglo (Cambia al arreglo)
    // 1) Enviamos el nuevo valor de la iteracion
    // 2) Nos devuelve un Nuevo Arreglo con los valores modificados
    val respuestaMap: List<Double> = arregloDinamico.map { valorActual: Int ->
        return@map valorActual.toDouble() + 100.00
    }

    val respuestaMapDos = arregloDinamico.map{ it + 15}
    println(respuestaMap)
    println(respuestaMapDos)

    // Filter -> Filtra el arreglo
    // 1) Devolver una expresion (True o False)
    // 2) Nuevo arreglo filtrado
    val respuestaFilter: List<Int> = arregloDinamico.filter {
            valorActual: Int ->
        val mayoresACinco: Boolean = valorActual > 5
        return@filter mayoresACinco
    }
    val respuestaFilterDos = arregloDinamico.filter { it <= 5 }
    println(respuestaFilter)
    println(respuestaFilterDos)

    // OR AND
    // OR -> Any (Alguno cumple)
    // AND -> All (todos cumplen)
    val respuestaAny: Boolean = arregloDinamico.any {
        valorActual: Int -> return@any (valorActual > 5)
    }
    println(respuestaAny) // True

    val respuestaAll: Boolean = arregloDinamico.all {
        valorActual: Int -> return@all (valorActual > 5)
    }
    println(respuestaAll) // False

    // REDUCE (acumulador) -> valor acumulado
    // valor acumulado = 0 (Siempre 0 en Kotlin)
    // [1,2,3,4,5] -> sumar todos los valores
    // Valor Iteracion 1 = empeiza + 1 = 0+1 = 1
    // Valor Iteracion 2 = empeiza1 + 2 = 1+2 = 3
    // Valor Iteracion 3 = empeiza2 + 3 = 3+3 = 6
    // Valor Iteracion 4 = empeiza3 + 4 = 6+4 = 10

    val respuestaReduce: Int = arregloDinamico.reduce { // acumulado = 0
        acumulado: Int,
        valorActual -> return@reduce (acumulado + valorActual)
    }
    println(respuestaReduce) // 78
}


// Void -> Unit
fun imprimirNombre(nombre: String): Unit {
    // Template Strings
    println("Nombre: ${nombre}")
}

fun calcularSueldo(
    sueldo: Double, // Requerido
    tasa: Double = 12.00, // Opcional por defecto (por el =)
    bonoEspecial: Double? = null // Opcion null -> nullable, puede ser double o null
): Double {
    // Int -> Int? (nullable)
    // String -> String? (nullable)
    // Date -> Date? (nullable)
    if (bonoEspecial == null){
        return sueldo * (100/tasa)
    }else{
        bonoEspecial.dec()
        return sueldo * (100/tasa) + bonoEspecial
    }
}

abstract class Numeros ( // Constructor Primario
    //Ejemplo:
    // uno: Int (Parametro sin modificador de acceso)
    // private var uno: Int (propiedad privada)
    protected val numeroUno: Int,
    protected val numeroDos: Int
){
  init {
      this.numeroUno; this.numeroDos; // this es opcional
      numeroUno; numeroDos // sin el this, es lo mismo
      println("Inicializado")
  }
}

// Constructor Primario Suma + Constructor del Padre
class Suma (uno: Int, dos: Int) : Numeros(uno, dos) {
    init { // Bloque constructor primario
        this.numeroUno; numeroUno;
        this.numeroUno; numeroDos;
    }

    // segundo constructor
    constructor(uno: Int?, dos: Int) : this( // llamo al constructor primario
        if (uno == null) 0 else uno,
        dos
    ) { // si necesitamos bloqueo de codigo lo usamos
        numeroUno
    }

    // tercer constructor
    constructor(uno: Int, dos: Int?) : this( // llamo al constructor primario
        uno,
        if (dos == null) 0 else dos
    ) // si no necesitamos bloqueo de codigo lo omitimos

    constructor(uno: Int?, dos: Int?) : this( // llamo al constructor primario
        if (uno == null) 0 else uno,
        if (dos == null) 0 else dos
    )
    // Método
    public fun sumar(): Int{
        val total = numeroUno + numeroDos
        agregarHistorial(total)
        return total
    }

    //J ava propiedades estáticas + Singleton
    companion object {
        // Atributos y Metodos Compartidos
        // entre instancias
        val pi = 3.14

        fun elevarAlCuadrado(num: Int): Int{
            return num * num
        }
        val historialSumas = arrayListOf<Int>()
        fun agregarHistorial(valorNuevaSuma: Int) {
            historialSumas.add(valorNuevaSuma)
        }
    }

}


