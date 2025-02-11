package com.app.atletismo.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.app.atletismo.data.entities.usuarios.LoginRequest
import com.app.atletismo.data.entities.usuarios.Usuario
import com.app.atletismo.databinding.ActivityLoginBinding
import com.app.atletismo.logic.LoginLogic
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginLogic = LoginLogic()
    private lateinit var usuario: Usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inflar el layout
        binding = ActivityLoginBinding.inflate(layoutInflater)

        // Configurar botón de login
        binding.loginButton.setOnClickListener {
            val email = binding.iptMail.text.toString().trim()
            val password = binding.iptPass.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                // Llamar a la función de login con los datos ingresados
                performLogin(email, password)

            } else {
                Toast.makeText(this, "Por favor ingresa tu correo y contraseña", Toast.LENGTH_SHORT).show()
            }
        }

        setContentView(binding.root)
    }

    private fun performLogin(email: String, password: String) {
        loginLogic.loginAdmin(LoginRequest(email, password), object : Callback<Usuario> {
            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                if(!response.isSuccessful){
                    when(response.code()){
                        401 -> Toast.makeText(this@LoginActivity, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                        403 -> Toast.makeText(this@LoginActivity, "Acceso denegado", Toast.LENGTH_SHORT).show()
                        else -> Toast.makeText(this@LoginActivity, "Error en la solicitud: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }else {
                    usuario = response.body()!!
                    startActivity()
                    Toast.makeText(this@LoginActivity, "Login exitoso", Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Error en la conexión", Toast.LENGTH_SHORT).show()
                Log.e("LoginActivity", "Error: ${t.message}")
            }
        })
    }

    private fun startActivity() {
        val intent = Intent(this, MainMenuActivity::class.java)
        startActivity(intent)
        finish()
    }
}
