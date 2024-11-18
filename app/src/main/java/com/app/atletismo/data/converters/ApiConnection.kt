package com.example.aplicacionmovil.data.converters

import com.app.atletismo.data.entities.utils.LocalDateDeserializer
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate

object ApiConnection {

    enum class typeApi { Atletismo }

    private val API_ATLETISMO = "https://backend-atletismo.onrender.com/API/Atletismo/"

    private fun getConnnection(base: String): Retrofit {
        val gson = GsonBuilder()
            .registerTypeAdapter(LocalDate::class.java, LocalDateDeserializer())
            .create()

        return Retrofit.Builder()
            .baseUrl(base)
            .addConverterFactory(GsonConverterFactory.create(gson))
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