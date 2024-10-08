package com.app.atletismo.data.endpoints

import com.app.atletismo.data.entities.campeonatos.Campeonato
import retrofit2.Response
import retrofit2.http.GET

interface CampeonatoEndpoint {

    /*@GET("campeonatos/dto")
    suspend fun getAllCampeonatos(): Response<List<Campeonato>>*/

    @GET("campeonatos")
    suspend fun getAllCampeonatos(): Response<List<Campeonato>>

}