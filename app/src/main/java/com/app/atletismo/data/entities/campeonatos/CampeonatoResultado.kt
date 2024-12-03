package com.app.atletismo.data.entities.campeonatos

import com.app.atletismo.logic.data.dto.CampeonatoDTO
import com.app.atletismo.logic.data.dto.PruebaDTO
import java.time.LocalDate
import java.util.ArrayList

data class CampeonatoResultado(
    val id: Int,
    val nombre: String,
    val organizador: String,
    val sede: String,
    val fechaInicio: LocalDate,
    val fechaFin: LocalDate
)

fun CampeonatoResultado.getCampeonatoResultadoDTO(): CampeonatoDTO {
    return CampeonatoDTO(
        id,
        nombre,
        organizador,
        sede,
        fechaInicio,
        fechaFin,
        null,
        listOf()
    )
}
