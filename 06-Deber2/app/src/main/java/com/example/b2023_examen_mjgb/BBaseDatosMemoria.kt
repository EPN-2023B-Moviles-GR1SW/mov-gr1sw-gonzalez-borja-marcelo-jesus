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
            arregloBCiudad.add(BCiudad(1,"Quito",35,1000,false,null))
            arregloBCiudad.add(BCiudad(2,"Manta",33,2000,true,"17-01-2024"))
            arregloBCiudad.add(BCiudad(3,"Tokio",32,3000,false, null))
            arregloBCiudad.add(BCiudad(4,"Jalisco",36,4000,true, "24-02-2024"))
        }
    }
}