package com.app.atletismo.logic.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Login(
    val id: Int,
    val email: String,
    val password: String
) : Parcelable
