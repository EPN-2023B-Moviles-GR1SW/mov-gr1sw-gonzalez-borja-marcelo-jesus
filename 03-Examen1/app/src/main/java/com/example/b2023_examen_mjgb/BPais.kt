package com.example.b2023_examen_mjgb

class BPais (var id: Int, var nombre: String?, var capital: String?, var poblacion: Int,
    var tasaCrecimiento: Double){
    constructor() : this(0, null, null,0,0.0)
    override fun toString(): String {
        return "${id}: " + "${nombre}"
    }
}