package com.app.atletismo.ui.activities

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.atletismo.data.entities.campeonatos.Resultado
import com.app.atletismo.data.entities.campeonatos.getResultado
import com.app.atletismo.databinding.ActivityCompetidorBinding
import com.app.atletismo.logic.ResultadoLogic
import com.app.atletismo.logic.data.dto.ResultadoDTO
import com.app.atletismo.ui.adapters.CompetidoresAdapterItems
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CompetidorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCompetidorBinding
    private lateinit var lmanager: LinearLayoutManager
    private val apiService = ResultadoLogic().getResultadoService()
    private var resultadosItems: MutableList<ResultadoDTO> = mutableListOf<ResultadoDTO>()
    private lateinit var competidoresAdapter: CompetidoresAdapterItems

    private var idCampeonato: Int = 0
    private var idPrueba: Int = 0

    private var retryCount = 0
    private val maxRetries = 3 // Número máximo de intentos
    private var retryDelayMillis = 5000L // Retraso inicial en milisegundos (5 segundos)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCompetidorBinding.inflate(layoutInflater)

        lmanager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

        competidoresAdapter = CompetidoresAdapterItems(resultadosItems) { it ->
            lanzarRegistrarResultadoActivity(it)
        }
        binding.competidoresRecyclerView.apply {
            this.layoutManager = lmanager
            this.adapter = competidoresAdapter
        }

        val nombrePrueba = intent.getStringExtra("nombrePrueba")!!

        binding.pruebaTitleTextView.text = nombrePrueba

        idCampeonato = intent.getIntExtra("idCampeonato", 0)
        idPrueba = intent.getIntExtra("idPrueba", 0)

        cargarResultados(idCampeonato,idPrueba)

        setContentView(binding.root)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val refrescar = intent.getBooleanExtra("refrescar", false)
        if (refrescar) {
            Toast.makeText(this, "Resultado Actualizado, cargando nuevos resultados... ", Toast.LENGTH_SHORT).show()
            cargarResultados(idCampeonato,idPrueba)
        }
    }

    private fun cargarResultados(idCampeonato: Int, idPrueba: Int) {
        apiService.getResultadosPorCampeonatoYPrueba(idCampeonato, idPrueba).enqueue(object : Callback<List<Resultado>> {
            override fun onResponse(call: Call<List<Resultado>>, response: Response<List<Resultado>>) {
                val resultados = response.body()
                if (resultados != null && resultados.isNotEmpty()) {
                    competidoresAdapter.updateData(resultados.map { it.getResultado() })
                    Toast.makeText(this@CompetidorActivity, "Resultados cargados correctamente", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@CompetidorActivity, "No se encontraron competidores inscritos en esta prueba", Toast.LENGTH_SHORT).show()
                    Handler().postDelayed({
                        finish()
                    }, 2000)
                }
            }

            override fun onFailure(call: Call<List<Resultado>>, t: Throwable) {
                Toast.makeText(this@CompetidorActivity, "Error al cargar resultados", Toast.LENGTH_SHORT).show()
                Log.e("CampeonatoActivity", "Error: ${t.message}")
                showToastAndRetry(idCampeonato, idPrueba)
            }
        })
    }
    private fun showToastAndRetry(idCampeonato: Int, idPrueba: Int) {
        if (retryCount < maxRetries) {
            retryCount++
            Toast.makeText(this, "Intentando conectar... intento $retryCount de $maxRetries", Toast.LENGTH_SHORT).show()

            CoroutineScope(Dispatchers.Main).launch {
                delay(retryDelayMillis)
                cargarResultados(idCampeonato,idPrueba)
            }
        } else {
            Toast.makeText(this, "No se pudo conectar con el servidor después de varios intentos. Intenta mas tarde", Toast.LENGTH_LONG).show()
        }
    }

    fun lanzarRegistrarResultadoActivity(resultado: ResultadoDTO) {
        Toast.makeText(this, "Competidor: ${resultado.apellidos.toString().uppercase()}, ${resultado.nombres.toString().uppercase()}", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, RegistrarResultadoActivity::class.java)
        intent.putExtra("id", resultado.id)
        intent.putExtra("criterio", resultado.criterio)
        if(resultado.registrado){
            intent.putExtra("registrado", true)
            when(resultado.criterio){
                "Puntos" -> {
                    intent.putExtra("puntaje", resultado.puntaje)
                    intent.putExtra("posicion", resultado.posicion)
                }
                "Distancia" -> {
                    intent.putExtra("distancia", resultado.distancia.toString())
                    intent.putExtra("posicion", resultado.posicion)
                    intent.putExtra("viento", resultado.viento.toString())
                }
                "Tiempo" -> {
                    intent.putExtra("marca", resultado.marca)
                    intent.putExtra("posicion", resultado.posicion)
                    intent.putExtra("viento", resultado.viento.toString())
                }
            }
        }
        startActivity(intent)
    }
}