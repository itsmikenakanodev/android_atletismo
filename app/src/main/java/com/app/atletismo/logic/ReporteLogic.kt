package com.app.atletismo.logic

import com.app.atletismo.data.endpoints.CampeonatoEndpoint
import com.app.atletismo.data.endpoints.ReporteEndpoint
import com.app.atletismo.data.endpoints.ResultadoEndpoint
import com.app.atletismo.data.entities.campeonatos.CampeonatoConteoGeneral
import com.app.atletismo.data.entities.campeonatos.CompetidorDetalle
import com.app.atletismo.data.entities.campeonatos.EventoContadorCompetidor
import com.example.aplicacionmovil.data.converters.ApiConnection
import retrofit2.Callback

class ReporteLogic {

    fun getReporteService(): ReporteEndpoint {

        var service = ApiConnection.getService(
            ApiConnection.typeApi.Atletismo,
            ReporteEndpoint::class.java
        )

        return service
    }

    fun obtenerConteoGeneralCampeonato(campeonatoId : Int, callback : Callback<CampeonatoConteoGeneral>) {
        val service = getReporteService()
        val call = service.obtenerConteoGeneralCampeonato(campeonatoId)
        call.enqueue(callback)
    }

    fun obtenerConteoPruebasCampeonato(campeonatoId : Int, callback : Callback<List<EventoContadorCompetidor>>) {
        val service = getReporteService()
        val call = service.obtenerConteoPruebasCampeonato(campeonatoId)
        call.enqueue(callback)
    }

    fun obtenerCompetidoresCampeonato(campeonatoId: Int, callback : Callback<List<CompetidorDetalle>>) {
        val service = getReporteService()
        val call = service.obtenerCompetidoresCampeonato(campeonatoId)
        call.enqueue(callback)
    }
}