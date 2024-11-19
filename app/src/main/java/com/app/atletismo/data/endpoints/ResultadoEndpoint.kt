package com.app.atletismo.data.endpoints


import com.app.atletismo.data.entities.campeonatos.Resultado
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ResultadoEndpoint {

    @GET("resultados/campeonato/{campeonatoId}/prueba/{pruebaId}")
    fun getResultadosPorCampeonatoYPrueba(@Path("campeonatoId") campeonatoId: Int, @Path("pruebaId") pruebaId: Int): Call<List<Resultado>>
}