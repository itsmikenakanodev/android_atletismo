package com.app.atletismo.logic.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class CampeonatoDTO(
    val id: Int,
    val nombre: String,
    val organizador: String,
    val sede: String,
    val fechaInicio: LocalDate,
    val fechaFin: LocalDate,
    var estado: String? = null,
    val pruebas: List<PruebaDTO>

) : Parcelable
