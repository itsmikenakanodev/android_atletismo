package com.app.atletismo.data.entities.campeonatos

import com.app.atletismo.logic.data.dto.CompetidorDetalleDTO
import java.math.BigDecimal

data class CompetidorDetalle(
    val nombreEvento: String,
    val criterio: String,
    val nombres: String,
    val apellidos: String,
    val provincia: String,
    val sexo: String,
    val categoria: String,
    val numeroCompetidor: String,
    val marca: String?,
    val distancia: BigDecimal?,
    val posicion: Int?,
    val puntaje: Int?,
    val viento: BigDecimal?
)

fun CompetidorDetalle.getCompetidorDetalleDTO(): CompetidorDetalleDTO {
    return CompetidorDetalleDTO(
        nombreEvento,
        criterio,
        nombres,
        apellidos,
        provincia,
        sexo,
        categoria,
        numeroCompetidor,
        marca,
        distancia,
        posicion,
        puntaje,
        viento
    )
}
