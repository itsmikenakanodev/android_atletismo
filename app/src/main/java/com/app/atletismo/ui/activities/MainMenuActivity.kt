package com.app.atletismo.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.app.atletismo.databinding.ActivityMainMenuBinding

class MainMenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainMenuBinding.inflate(layoutInflater)

        binding.registerResultsCard.setOnClickListener {
            startActivity(CampeonatoActivity::class.java)
        }
        binding.viewResultsCard.setOnClickListener {
            Toast.makeText(this, "En construcción..", Toast.LENGTH_LONG).show()
        }

        binding.logoutButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        setContentView(binding.root)
    }

    private fun startActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }
}