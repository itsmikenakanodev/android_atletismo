package com.app.atletismo.data.entities.campeonatos

import com.app.atletismo.logic.data.dto.CampeonatoDTO
import java.time.LocalDate

data class Campeonato(
    val id: Int,
    val nombre: String,
    val organizador: String,
    val sede: String,
    val fechaInicio: LocalDate,
    val fechaFin: LocalDate,
    val pruebas: List<Prueba>
)

fun Campeonato.getCampeonato(): CampeonatoDTO {
    return CampeonatoDTO(
        id,
        nombre,
        organizador,
        sede,
        fechaInicio,
        fechaFin,
        null,
        pruebas.map { it.getPrueba() }
    )
}
