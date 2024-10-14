package com.app.atletismo.logic

import com.app.atletismo.logic.data.Login

class LoginLogic {

    fun getAllLogins(): ArrayList<Login> {
        val loginList = arrayListOf(
            Login(1, "user1@example.com", "password1"),
            Login(2, "user2@example.com", "password2"),
            Login(3, "user3@example.com", "password3"),
            Login(4, "user4@example.com", "password4"),
            Login(5, "user5@example.com", "password5")
        )

        return loginList
    }
}
