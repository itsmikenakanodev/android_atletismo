package com.app.atletismo.data.entities.campeonatos

import com.app.atletismo.logic.data.dto.CampeonatoConteoGeneralDTO

data class CampeonatoConteoGeneral(
    val championshipName: String,
    val maleCompetitors: Int,
    val femaleCompetitors: Int
)

fun CampeonatoConteoGeneral.getCampeonatoConteoGeneralDTO(): CampeonatoConteoGeneralDTO {
    return CampeonatoConteoGeneralDTO(championshipName, maleCompetitors, femaleCompetitors)
}
