package com.example.b2023_examen_mjgb

class BPais(
    var id: String?,
    var nombre: String?,
    var capital: String?,
    var poblacion: Int?,
    var tasaCrecimiento: Double?,
    var ciudades: List<BCiudad>?
) {
    constructor() : this(null, null, null, 0, 0.0, null)

    override fun toString(): String {
        return "${nombre}"
    }
}