package com.app.atletismo.data.entities.campeonatos

import com.app.atletismo.logic.data.Campeonatos

data class Campeonato(
    val id: Int,
    val nombre: String
)

fun Campeonato.getCampeonato(): Campeonatos {
    return Campeonatos(
        id,
        nombre
    )
}
