package com.app.atletismo.logic.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EventoContadorCompetidorDTO(
    val eventName: String,
    val maleCompetitors: Int,
    val femaleCompetitors: Int
): Parcelable
