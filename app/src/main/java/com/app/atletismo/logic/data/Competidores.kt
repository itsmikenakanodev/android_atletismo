package com.app.atletismo.logic.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Competidores(
    val id: Int,
    val name: String,
    val estado: String
): Parcelable
