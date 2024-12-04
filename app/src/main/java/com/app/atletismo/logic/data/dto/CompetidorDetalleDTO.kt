package com.app.atletismo.logic.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Parcelize
data class CompetidorDetalleDTO(
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
): Parcelable
