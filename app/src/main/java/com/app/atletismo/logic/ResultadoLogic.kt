package com.app.atletismo.logic

import com.app.atletismo.data.endpoints.CampeonatoEndpoint
import com.app.atletismo.data.endpoints.ResultadoEndpoint
import com.app.atletismo.data.entities.campeonatos.Resultado
import com.example.aplicacionmovil.data.converters.ApiConnection
import retrofit2.Callback

class ResultadoLogic {

    fun getResultadoService(): ResultadoEndpoint {

        var service = ApiConnection.getService(
            ApiConnection.typeApi.Atletismo,
            ResultadoEndpoint::class.java
        )

        return service
    }

    fun getResultadosPorCampeonatoYPrueba(idCampeonato : Int, idPrueba : Int,callback : Callback<List<Resultado>>) {
        val service = getResultadoService()
        val call = service.getResultadosPorCampeonatoYPrueba(idCampeonato, idPrueba)
        call.enqueue(callback)
    }
}