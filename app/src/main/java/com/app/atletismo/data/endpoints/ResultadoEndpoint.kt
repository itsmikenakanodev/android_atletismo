package com.app.atletismo.data.endpoints


import com.app.atletismo.data.entities.campeonatos.Resultado
import com.app.atletismo.data.entities.campeonatos.ResultadoRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ResultadoEndpoint {

    @GET("resultados/campeonato/{campeonatoId}/prueba/{pruebaId}")
    fun getResultadosPorCampeonatoYPrueba(@Path("campeonatoId") campeonatoId: Int, @Path("pruebaId") pruebaId: Int): Call<List<Resultado>>

    @PUT("resultados/{id}")
    fun actualizarResultado(@Path("id") id: Int, @Body resultado: ResultadoRequest): Call<Boolean>
}