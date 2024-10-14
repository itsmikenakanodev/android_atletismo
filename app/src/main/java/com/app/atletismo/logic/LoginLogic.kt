package com.app.atletismo.logic

import com.app.atletismo.logic.data.Login
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

class LoginLogic {

    private val client = OkHttpClient()

    // Obtiene todos los logins desde la API
    suspend fun getAllLogins(): List<Login>? {
        return withContext(Dispatchers.IO) {
            try {
                val request = Request.Builder()
                    .url("https://67056a3e031fd46a830fe008.mockapi.io/API/Atletismo/login")
                    .get()
                    .build()

                // Ejecutar la solicitud y obtener la respuesta
                client.newCall(request).execute().use { response ->
                    // Comprobar si la respuesta es exitosa
                    if (response.isSuccessful) {
                        // Utilizar body() en lugar de body
                        val responseBody = response.body()
                        if (responseBody != null) {
                            // Parsear la respuesta a una lista de Logins
                            Gson().fromJson(responseBody.charStream(), Array<Login>::class.java).toList()
                        } else {
                            null // Si el cuerpo es null, devolver null
                        }
                    } else {
                        null // Si la respuesta no es exitosa, devolver null
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null // En caso de error, devolver null
            }
        }
    }
}
