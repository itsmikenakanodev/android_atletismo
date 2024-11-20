package com.app.atletismo.data.endpoints

import com.app.atletismo.data.entities.usuarios.LoginRequest
import com.app.atletismo.data.entities.usuarios.Usuario
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthEndpoint {

    @POST("auth/login/admin")
    fun loginAdmin(@Body loginRequest: LoginRequest): Call<Usuario>
}