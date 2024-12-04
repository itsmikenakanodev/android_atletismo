package com.app.atletismo.data.entities.campeonatos

import com.app.atletismo.logic.data.dto.EventoContadorCompetidorDTO

data class EventoContadorCompetidor(
    val eventName: String,
    val maleCompetitors: Int,
    val femaleCompetitors: Int
)

fun EventoContadorCompetidor.getEventoContadorCompetidorDTO(): EventoContadorCompetidorDTO {
    return EventoContadorCompetidorDTO(eventName, maleCompetitors, femaleCompetitors)
}
