import java.util.Date

fun main() {
    println("Hello World!")

    // Tipos de variables
    // Inmutables (no se reasignan)
    val inmutable: String = "Adrian";

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
    val coqueten = if (estadoCivilWhen == "S") "Si" else "NO"

    calcularSueldo(10.00)
    calcularSueldo(10.00, 15.00)
    calcularSueldo(10.00, 12.00, 20.00)
    calcularSueldo(sueldo = 10.00, tasa = 12.00, bonoEspecial = 20.00) // Parametros nombrados
    calcularSueldo(10.00, bonoEspecial = 20.00) // Named Parameters
    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 14.00) // Parametros nombrados

    val sumaUno = Suma(1,1)
    val sumaDos = Suma(null,1)
    val sumaTres = Suma(1,null)
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
        if (dos == null) 0 else uno
    ) // si no necesitamos bloqueo de codigo lo omitimos
}
