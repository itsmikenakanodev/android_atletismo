package com.app.atletismo.data.endpoints

import com.app.atletismo.data.entities.campeonatos.Log
import retrofit2.Response
import retrofit2.http.GET

interface LoginEndpoint {

    @GET("login")
    suspend fun getAllUsers(): Response<List<Log>>

}