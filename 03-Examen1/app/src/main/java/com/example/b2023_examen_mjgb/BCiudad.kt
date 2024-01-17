package com.example.b2023_examen_mjgb

class BCiudad (var id: Int, var nombre: String? ,var idPaisCorresponde: Int, var poblacion: Int,
    var esCapital: Boolean){
    constructor() : this(0, null, 0,0, false)
    override fun toString(): String {
        return "${id}: " + "${nombre}"
    }
}