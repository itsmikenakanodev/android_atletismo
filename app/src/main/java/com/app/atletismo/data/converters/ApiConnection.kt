package com.example.aplicacionmovil.data.converters

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConnection {

    enum class typeApi { Atletismo }

    private val API_ATLETISMO = "https://67056a3e031fd46a830fe008.mockapi.io/API/Atletismo/"

    private fun getConnnection(base: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(base)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun <T, E : Enum<E>> getService(api: E, service: Class<T>): T {
        var BASE = ""
        when (api.name) {
            typeApi.Atletismo.name -> {
                BASE = API_ATLETISMO
            }
        }
        return getConnnection(BASE).create(service)
    }

}