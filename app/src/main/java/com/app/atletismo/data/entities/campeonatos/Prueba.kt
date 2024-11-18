package com.app.atletismo.data.entities.campeonatos

import com.app.atletismo.logic.data.dto.PruebaDTO

data class Prueba(
    val id: Int,
    val nombre: String,
    val tipo: String,
    val categoria: String
)

fun Prueba.getPrueba(): PruebaDTO {
    return PruebaDTO(
        id,
        nombre,
        tipo,
        categoria
    )
}
