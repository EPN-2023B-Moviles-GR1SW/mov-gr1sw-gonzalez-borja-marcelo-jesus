package com.example.b2023_examen_mjgb

class BBaseDatosMemoria {
    companion object{
        val arregloBPais = arrayListOf<BPais>()
        init {
            arregloBPais.add(BPais(1,"Ecuador","Quito",1000,1.1))
            arregloBPais.add(BPais(2,"Japon","Tokio",2000,1.2))
            arregloBPais.add(BPais(3,"Mexico","CDMX",3000,1.3))
        }

        val arregloBCiudad = arrayListOf<BCiudad>()
        init {
            arregloBCiudad.add(BCiudad(1,"Quito",1,1000,false))
            arregloBCiudad.add(BCiudad(2,"Manta",1,2000,true))
            arregloBCiudad.add(BCiudad(3,"Tokio",2,3000,false))
            arregloBCiudad.add(BCiudad(4,"Jalisco",3,4000,true))
        }
    }
}