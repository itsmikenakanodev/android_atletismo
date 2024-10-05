package com.app.atletismo.ui.theme.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.atletismo.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val emailEditText = findViewById<EditText>(R.id.ipt_mail)
        val passwordEditText = findViewById<EditText>(R.id.ipt_pass)
        val loginButton = findViewById<Button>(R.id.loginButton)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor ingrese ambos campos", Toast.LENGTH_SHORT).show()
            } else {
                // Lógica de autenticación
                Toast.makeText(this, "Iniciar sesión exitoso", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
