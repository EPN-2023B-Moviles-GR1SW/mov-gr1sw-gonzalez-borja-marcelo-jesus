package com.example.b2023_examen_mjgb

class BBaseDatosMemoria {
    companion object{
        val arregloBPais = arrayListOf<BPais>()
        init {
            arregloBPais.add(BPais(1,"Ecuador","Quito"))
            arregloBPais.add(BPais(2,"Japon","Tokio"))
            arregloBPais.add(BPais(3,"Mexico","CDMX"))
        }

        val arregloBCiudad = arrayListOf<BCiudad>()
        init {
            arregloBCiudad.add(BCiudad(1,"Quito",1))
            arregloBCiudad.add(BCiudad(2,"Manta",1))
            arregloBCiudad.add(BCiudad(3,"Tokio",2))
            arregloBCiudad.add(BCiudad(4,"Jalisco",3))
        }
    }
}