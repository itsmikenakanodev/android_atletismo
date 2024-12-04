package com.app.atletismo.logic.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.math.BigDecimal
import kotlin.time.Duration

@Parcelize
data class ResultadoDTO(val id: Int,
                        val marca: String?,
                        val distancia: BigDecimal?,
                        val posicion: Int?,
                        val puntaje: Int?,
                        val viento: BigDecimal?,
                        val competidorId: Int,
                        val categoria: String?,
                        val usuarioId: Int,
                        val nombres: String?,
                        val apellidos: String?,
                        val numeroSocio: Int?) : Parcelable
