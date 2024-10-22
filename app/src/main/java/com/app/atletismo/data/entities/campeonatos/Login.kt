package com.app.atletismo.data.entities.campeonatos

import com.app.atletismo.logic.data.Campeonatos
import com.app.atletismo.logic.data.Login

data class Log(
    val id: Int,
    val email: String,
    val password: String
)

fun Log.getUsers(): Login {
    return Login(
        id,
        email,
        password
    )
}