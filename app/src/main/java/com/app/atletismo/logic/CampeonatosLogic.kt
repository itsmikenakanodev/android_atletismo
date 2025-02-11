package com.app.atletismo.logic

import com.app.atletismo.data.endpoints.CampeonatoEndpoint
import com.app.atletismo.data.entities.campeonatos.Campeonato
import com.app.atletismo.data.entities.campeonatos.CampeonatoResultado
import com.app.atletismo.data.entities.campeonatos.getCampeonato
import com.app.atletismo.logic.data.dto.CampeonatoDTO
import com.example.aplicacionmovil.data.converters.ApiConnection
import retrofit2.Callback

class CampeonatosLogic {

    suspend fun getAllCampeonatosAPI(): ArrayList<CampeonatoDTO> {
        var itemList = arrayListOf<CampeonatoDTO>()

        val response = ApiConnection.getService(
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

        val service = ApiConnection.getService(
            ApiConnection.typeApi.Atletismo,
            CampeonatoEndpoint::class.java
        )

        return service
    }

    fun obtenerCampeonatos(anio: Int, mes: Int, callback: Callback<List<Campeonato>>) {
        val service = getCampeonatoService()
        val call = service.obtenerCampeonatos(anio, mes)
        call.enqueue(callback)
    }

    fun obtenerCampeonatosSinPruebas(anio: Int, mes: Int, callback: Callback<List<CampeonatoResultado>>) {
        val service = getCampeonatoService()
        val call = service.obtenerCampeonatosSinPruebas(anio, mes)
        call.enqueue(callback)
    }
}