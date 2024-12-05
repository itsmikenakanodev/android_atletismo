package com.app.atletismo.data.entities.campeonatos

import com.app.atletismo.logic.data.dto.CampeonatoDTO
import com.app.atletismo.logic.data.dto.ResultadoDTO
import java.math.BigDecimal
import kotlin.time.Duration

data class Resultado(
    val id: Int,
    val marca: String?,
    val distancia: BigDecimal?,
    val posicion: Int?,
    val puntaje: Int?,
    val viento: BigDecimal?,
    val registrado: Boolean,
    val competidorId: Int,
    val categoria: String?,
    val usuarioId: Int,
    val nombres: String?,
    val apellidos: String?,
    val numeroSocio: Int?,
    val criterio: String
)

fun Resultado.getResultado(): ResultadoDTO {
    return ResultadoDTO(
        id,
        marca,
        distancia,
        posicion,
        puntaje,
        viento,
        registrado,
        competidorId,
        categoria,
        usuarioId,
        nombres,
        apellidos,
        numeroSocio,
        criterio
    )
}
