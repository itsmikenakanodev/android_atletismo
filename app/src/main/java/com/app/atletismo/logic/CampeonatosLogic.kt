package com.app.atletismo.logic

import com.app.atletismo.data.endpoints.CampeonatoEndpoint
import com.app.atletismo.data.entities.campeonatos.getCampeonato
import com.app.atletismo.logic.data.dto.CampeonatoDTO
import com.example.aplicacionmovil.data.converters.ApiConnection

class CampeonatosLogic {

    suspend fun getAllCampeonatosAPI(): ArrayList<CampeonatoDTO> {
        var itemList = arrayListOf<CampeonatoDTO>()

        var response = ApiConnection.getService(
            ApiConnection.typeApi.Atletismo,
            CampeonatoEndpoint::class.java
        ).getAllCampeonatos()

        if (response.isSuccessful) {
            response.body()!!.forEach {
                val m = it.getCampeonato()
                itemList.add(m)
            }
        }
        return itemList
    }

    fun getCampeonatoService(): CampeonatoEndpoint {

        var service = ApiConnection.getService(
            ApiConnection.typeApi.Atletismo,
            CampeonatoEndpoint::class.java
        )

        return service
    }
}