package com.app.atletismo.logic.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Campeonatos(
    val id: Int,
    val title: String) : Parcelable
