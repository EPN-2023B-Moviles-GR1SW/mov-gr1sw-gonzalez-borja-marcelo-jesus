package com.example.b2023_examen_mjgb

class BPais (var id: Int, var nombre: String?, var capital: String?){
    constructor() : this(0, null, null)
    override fun toString(): String {
        return "${id}: " + "${nombre}"
    }
}