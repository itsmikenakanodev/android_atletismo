package com.app.atletismo.ui.activities

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.app.atletismo.data.entities.campeonatos.ResultadoRequest
import com.app.atletismo.databinding.ActivityRegistrarResultadoBinding
import com.app.atletismo.logic.ResultadoLogic
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.BigDecimal

class RegistrarResultadoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrarResultadoBinding
    private val apiService = ResultadoLogic().getResultadoService()

    private lateinit var pendingIntent: PendingIntent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRegistrarResultadoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val registrado = intent.getBooleanExtra("registrado", false)
        val criterio = intent.getStringExtra("criterio")
        if (registrado){
            binding.guardarButton.text = "Actualizar"
            when(criterio) {
                "Tiempo" -> {
                    var marca = intent.getStringExtra("marca")
                    if (marca ==null) {
                        marca = "00:00:00"
                    }
                    val viento = intent.getStringExtra("viento")
                    val posicion = intent.getIntExtra("posicion", 0).toString()
                    binding.minutosEditText.text = Editable.Factory.getInstance().newEditable(marca.split(":")[0])
                    binding.segundosEditText.text = Editable.Factory.getInstance().newEditable(marca.split(":")[1])
                    binding.milisegundosEditText.text = Editable.Factory.getInstance().newEditable(marca.split(":")[2])
                    binding.vientoEditText.text = Editable.Factory.getInstance().newEditable(viento)
                    binding.posicionEditText.text = Editable.Factory.getInstance().newEditable(posicion)
                }
                "Distancia" -> {
                    val distancia = intent.getStringExtra("distancia")
                    val viento = intent.getStringExtra("viento")
                    val posicion = intent.getIntExtra("posicion", 0).toString()
                    binding.distanciaEditText.text = Editable.Factory.getInstance().newEditable(distancia)
                    binding.vientoEditText.text = Editable.Factory.getInstance().newEditable(viento)
                    binding.posicionEditText.text = Editable.Factory.getInstance().newEditable(posicion)
                }
                "Puntos" -> {
                    val puntaje = intent.getStringExtra("puntaje")
                    val posicion = intent.getIntExtra("posicion", 0).toString()
                    binding.puntajeEditText.text = Editable.Factory.getInstance().newEditable(puntaje)
                    binding.posicionEditText.text = Editable.Factory.getInstance().newEditable(posicion)
                }
            }
        }
        when(criterio) {
            "Tiempo" -> {
                binding.distanciaEditText.visibility = android.view.View.GONE
                binding.puntajeEditText.visibility = android.view.View.GONE
                binding.distanciaLabel.visibility = android.view.View.GONE
                binding.puntajeLabel.visibility = android.view.View.GONE
            }
            "Distancia" -> {
                binding.minutosEditText.visibility = android.view.View.GONE
                binding.segundosEditText.visibility = android.view.View.GONE
                binding.milisegundosEditText.visibility = android.view.View.GONE
                binding.tiempoLabel.visibility = android.view.View.GONE
                binding.puntajeLabel.visibility = android.view.View.GONE
                binding.puntajeEditText.visibility = android.view.View.GONE
            }
            "Puntos" -> {
                binding.minutosEditText.visibility = android.view.View.GONE
                binding.segundosEditText.visibility = android.view.View.GONE
                binding.milisegundosEditText.visibility = android.view.View.GONE
                binding.vientoEditText.visibility = android.view.View.GONE
                binding.tiempoLabel.visibility = android.view.View.GONE
                binding.vientoLabel.visibility = android.view.View.GONE
                binding.distanciaEditText.visibility = android.view.View.GONE
                binding.distanciaLabel.visibility = android.view.View.GONE
            }
        }
        binding.guardarButton.setOnClickListener {
            verificarInput(criterio)
        }

    }

    private fun registrarResultado(resultado: ResultadoRequest) {
        apiService.actualizarResultado(resultado.id, resultado).enqueue(object :
            Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.isSuccessful && response.body() != null) {
                    val intent =
                        Intent(this@RegistrarResultadoActivity, CompetidorActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    intent.putExtra("refrescar", true)
                    startActivity(intent)
                    finish()
                } else {
                    finish()
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Toast.makeText(
                    this@RegistrarResultadoActivity,
                    "Error en la conexión",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e("RegistrarResultadoActivity", "Error: ${t.message}")
            }
        })
    }

    private fun verificarInput(criterio: String?): Boolean {
        val posicion = binding.posicionEditText.text.toString().toInt()

        // Verificar posiciones
        if (posicion == 0) {
            Toast.makeText(this, "Posición inválida", Toast.LENGTH_SHORT).show()
            return false
        }

        if (posicion < 1 || posicion > 10) {
            Toast.makeText(this, "Posición fuera de rango", Toast.LENGTH_SHORT).show()
            return false
        }

        var viento: Double? = null
        var distancia: Double? = null
        var tiempo: String? = null
        var puntaje: Int? = null

        // Verificar si el viento es un número decimal válido
        val vientoEditText = binding.vientoEditText.text.toString().replace(',', '.')
        if (!vientoEditText.contains(".") || vientoEditText.split("\\.".toRegex())
                .dropLastWhile { it.isEmpty() }.toTypedArray()[1].length > 2
        ) {
            Toast.makeText(this, "Viento inválido", Toast.LENGTH_SHORT).show()
            return false
        }

        val vientoFloat = vientoEditText.toFloat()
        if (vientoFloat < -2 || vientoFloat > 2) {
            Toast.makeText(this, "Viento fuera de rango", Toast.LENGTH_SHORT).show()
            return false
        }
        viento = vientoFloat.toDouble()

        // Verificar si el criterio es "Tiempo"
        if (criterio == "Tiempo") {
            // Verificar si el tiempo es válido
            val minutos = binding.minutosEditText.text.toString()
            val segundos = binding.segundosEditText.text.toString()
            val milisegundos = binding.milisegundosEditText.text.toString()

            if (minutos.isEmpty() || segundos.isEmpty() || milisegundos.isEmpty()) {
                Toast.makeText(this, "Tiempo inválido", Toast.LENGTH_SHORT).show()
                return false
            }

            val minutosInt = minutos.toInt()
            val segundosInt = segundos.toInt()
            val milisegundosInt = milisegundos.toInt()

            if (minutosInt < 0 || minutosInt > 59) {
                Toast.makeText(this, "Minutos fuera de rango", Toast.LENGTH_SHORT).show()
                return false
            }

            if (segundosInt < 0 || segundosInt > 59) {
                Toast.makeText(this, "Segundos fuera de rango", Toast.LENGTH_SHORT).show()
                return false
            }

            if (milisegundosInt < 0 || milisegundosInt > 999) {
                Toast.makeText(this, "Milisegundos fuera de rango", Toast.LENGTH_SHORT).show()
                return false
            }

            tiempo = minutos + ":" + segundos + ":" + milisegundos
        }
        // Verificar si el criterio es "Puntos"
        else if (criterio == "Puntos") {
            // Verificar si el puntaje es válido
            val puntajeEditText = binding.puntajeEditText.text.toString().toInt()
            if (puntajeEditText < 0) {
                Toast.makeText(this, "Puntaje no puede ser negativo", Toast.LENGTH_SHORT).show()
                return false
            }
            puntaje = puntajeEditText
            viento = 0.0
        }
        // Verificar si el criterio es "Distancia"
        else if (criterio == "Distancia") {
            // Verificar si la distancia es un número decimal válido
            val distanciaEditText = binding.distanciaEditText.text.toString().replace(',', '.')
            if (!distanciaEditText.contains(".") || distanciaEditText.split("\\.".toRegex())
                    .dropLastWhile { it.isEmpty() }.toTypedArray()[1].length > 2
            ) {
                Toast.makeText(this, "Distancia inválida", Toast.LENGTH_SHORT).show()
                return false
            }

            val distanciaFloat = distanciaEditText.toFloat()
            if (distanciaFloat < 0) {
                Toast.makeText(this, "Distancia no puede ser negativa", Toast.LENGTH_SHORT).show()
                return false
            }
            distancia = distanciaFloat.toDouble()
        }


        val resultado = ResultadoRequest(
            id = intent.getIntExtra("id", 0),
            marca = tiempo,
            distancia = distancia?.let { BigDecimal(it) },
            puntaje = puntaje,
            posicion = posicion,
            viento = BigDecimal(viento)
        )

        registrarResultado(resultado)

        return true
    }
}