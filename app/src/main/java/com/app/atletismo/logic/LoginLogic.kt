package com.app.atletismo.logic

import com.app.atletismo.data.endpoints.LoginEndpoint
import com.app.atletismo.data.entities.campeonatos.Log
import com.app.atletismo.logic.data.Login
import com.example.aplicacionmovil.data.converters.ApiConnection
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Response

class LoginLogic {

    // Método para obtener todos los logins desde la API
    suspend fun getAllLoginsAPI(): List<Log>? {
        return withContext(Dispatchers.IO) {
            try {
                // Realizar la llamada a la API usando Retrofit
                val response: Response<List<Log>> = ApiConnection.getService(
                    ApiConnection.typeApi.Atletismo,
                    LoginEndpoint::class.java
                ).getAllUsers()

                if (response.isSuccessful && response.body() != null) {
                    response.body() // Devolver la lista de logins
                } else {
                    null // Si no es exitosa, devolver null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null // En caso de error, devolver null
            }
        }
    }
}
