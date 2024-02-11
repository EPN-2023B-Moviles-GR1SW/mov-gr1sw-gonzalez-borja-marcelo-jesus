package com.example.deber3_gr1sw_mjbg

import android.graphics.Color

class BBaseDatosMemoria {
    // EMPEZAR EL COMPANION OBJECT
    companion object{
        val arregloBProyectos = arrayListOf<BProyectos>()
        init {
            arregloBProyectos.add(BProyectos(1, "Universidad",
                "tareas de universidad", Color.rgb(0,0,255)))
            arregloBProyectos.add(BProyectos(2, "Trabajo",
                "tareas del trabajo", Color.rgb(0,255,0)))
            arregloBProyectos.add(BProyectos(3, "Cursos",
                "tareas de los cursos", Color.rgb(255,0,0)))
            arregloBProyectos.add(BProyectos(4, "Otros",
                "tareas de otrsas cosas", Color.rgb(255,0,255)))
        }

        val arregloBTareas= arrayListOf<BTareas>()
        init {
            arregloBTareas.add(BTareas(1, "Universidad",
                "tareas de universidad", "sabado"))
            arregloBTareas.add(BTareas(2, "Trabajo",
                "tareas del trabajo", "20 de Marzo"))
            arregloBTareas.add(BTareas(3, "Cursos",
                "tareas de los cursos", "domingo"))
            arregloBTareas.add(BTareas(4, "Otros",
                "tareas de otrsas cosas", "15 de Marzo"))
        }


    }
}