package com.app.atletismo.data.endpoints

import com.app.atletismo.data.entities.campeonatos.CampeonatoConteoGeneral
import com.app.atletismo.data.entities.campeonatos.CompetidorDetalle
import com.app.atletismo.data.entities.campeonatos.EventoContadorCompetidor
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ReporteEndpoint {

    @GET("reportes/campeonatos/{idCampeonato}")
    fun obtenerConteoGeneralCampeonato(
        @Path("idCampeonato") idCampeonato: Int
    ): Call<CampeonatoConteoGeneral>

    @GET("reportes/campeonatos/{idCampeonato}/pruebas")
    fun obtenerConteoPruebasCampeonato(
        @Path("idCampeonato") idCampeonato: Int
    ): Call<List<EventoContadorCompetidor>>

    @GET("reportes/campeonatos/{idCampeonato}/competidores-detalle")
    fun obtenerCompetidoresCampeonato(
        @Path("idCampeonato") idCampeonato: Int
    ): Call<List<CompetidorDetalle>>
}