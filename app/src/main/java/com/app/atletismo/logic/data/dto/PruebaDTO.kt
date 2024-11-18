package com.app.atletismo.logic.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PruebaDTO(
    val id: Int,
    val nombre: String,
    val tipo: String,
    val categoria: String? = null,
    var estado: String? = null // El estado de la prueba, puede ser "proxima", "en curso" o "finalizada"
) : Parcelable
