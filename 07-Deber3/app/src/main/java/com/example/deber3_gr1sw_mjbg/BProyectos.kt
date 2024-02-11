package com.example.deber3_gr1sw_mjbg

import android.graphics.Color

class BProyectos (var id: Int, var nombre: String?, var descripcion: String?, var color: Int) {
    constructor() : this(0, null, null, Color.rgb(0,0,0))
    override fun toString(): String {
        return "${nombre} - ${descripcion}"
    }
}