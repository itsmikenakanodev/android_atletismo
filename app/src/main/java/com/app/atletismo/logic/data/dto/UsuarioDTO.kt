package com.app.atletismo.logic.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UsuarioDTO(
    val id: Int,
    val nombres: String,
    val apellidos: String,
    val email: String,
    val idRol: Int,
    val rol: String
): Parcelable
