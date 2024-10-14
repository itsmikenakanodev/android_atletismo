package com.app.atletismo.ui.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.app.atletismo.databinding.ActivityLoginBinding
import com.app.atletismo.logic.LoginLogic
import com.app.atletismo.logic.data.Login
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inflar el layout
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
    }

    private fun performLogin(email: String, password: String) {
        lifecycleScope.launch {
            val logins = LoginLogic().getAllLogins() // Llamar a la función para obtener logins

            if (logins != null) {
                // Depuración
                Log.d("LoginActivity", "Email: $email, Password: $password")

                // Buscar el usuario que coincida con el email y la contraseña
                val userExists = logins.any { it.email == email && it.password == password }

                if (userExists) {
                    // Si se encuentra el usuario, el inicio de sesión es exitoso
                    Toast.makeText(this@LoginActivity, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                    // Navegar a la siguiente actividad o pantalla
                } else {
                    // Si no se encuentra el usuario, mostrar un mensaje de error
                    Toast.makeText(this@LoginActivity, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this@LoginActivity, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
