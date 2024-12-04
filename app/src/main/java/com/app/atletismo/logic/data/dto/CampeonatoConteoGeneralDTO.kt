package com.app.atletismo.logic.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CampeonatoConteoGeneralDTO(
    val championshipName: String,
    val maleCompetitors: Int,
    val femaleCompetitors: Int
): Parcelable
