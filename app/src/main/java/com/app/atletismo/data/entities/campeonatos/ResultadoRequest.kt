package com.app.atletismo.data.entities.campeonatos

import java.math.BigDecimal

data class ResultadoRequest(
    val id: Int,
    val marca: String?,
    val distancia: BigDecimal?,
    val posicion: Int?,
    val puntaje: Int?,
    val viento: BigDecimal?
)
