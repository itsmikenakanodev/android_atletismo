package com.app.atletismo.logic

import com.app.atletismo.data.endpoints.AuthEndpoint
import com.app.atletismo.data.endpoints.ResultadoEndpoint
import com.app.atletismo.data.entities.usuarios.LoginRequest
import com.app.atletismo.data.entities.usuarios.Usuario
import com.app.atletismo.logic.data.Login
import com.example.aplicacionmovil.data.converters.ApiConnection
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Callback
import javax.security.auth.login.LoginException

class LoginLogic {

    fun getLoginService(): AuthEndpoint {

        var service = ApiConnection.getService(
            ApiConnection.typeApi.Atletismo,
            AuthEndpoint::class.java
        )

        return service
    }

    fun loginAdmin(loginRequest:LoginRequest, callback : Callback<Usuario>) {
        val apiService = getLoginService()
        val call =apiService.loginAdmin(loginRequest)
        call.enqueue(callback)
    }
}
