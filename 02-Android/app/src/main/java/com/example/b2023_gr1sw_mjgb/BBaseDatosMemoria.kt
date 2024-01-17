package com.example.b2023_gr1sw_mjgb

class BBaseDatosMemoria {
    // EMPEZAR EL COMPANION OBJECT
    companion object{
        val arregloBEntrenador = arrayListOf<BEntrenador>()
        init {
            arregloBEntrenador.add(BEntrenador(1, "Jesus", "j@j.com"))
            arregloBEntrenador.add(BEntrenador(2, "Marcelo", "m@m.com"))
            arregloBEntrenador.add(BEntrenador(3, "Luis", "l@l.com"))
        }
    }
}

