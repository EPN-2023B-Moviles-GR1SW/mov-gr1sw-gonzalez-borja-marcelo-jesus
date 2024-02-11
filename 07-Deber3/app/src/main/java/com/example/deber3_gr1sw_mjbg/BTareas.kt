package com.example.deber3_gr1sw_mjbg

class BTareas (var id: Int, var nombre: String?, var descripcion: String?, var fecha: String?){
    constructor() : this(0, null, null, null)
    override fun toString(): String {
        return "${nombre} - ${descripcion}"
    }
}