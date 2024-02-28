package com.example.b2023_examen_mjgb

class BCiudad(
    var id: String?,
    var nombre: String?,
    var poblacion: Int?,
    var esCapital: Boolean?,
    var fechaFund: String?
) {
    override fun toString(): String {
        return "${nombre}"
    }
}