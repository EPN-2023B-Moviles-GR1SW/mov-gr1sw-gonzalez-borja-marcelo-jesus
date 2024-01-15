package com.example.b2023_examen_mjgb

class BCiudad (var id: Int, var nombre: String? ,var idPaisCorresponde: Int){
    constructor() : this(0, null, 0)
    override fun toString(): String {
        return "${id}: " + "${nombre}"
    }
}