package com.app.atletismo.ui.activities

import android.os.Bundle
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

        val idCampeonato = intent.getIntExtra("idCampeonato", 0)
        val idPrueba = intent.getIntExtra("idPrueba", 0)
        val nombrePrueba = intent.getStringExtra("nombrePrueba")!!

        binding.pruebaTitleTextView.text = nombrePrueba

        cargarResultados(idCampeonato,idPrueba)

        setContentView(binding.root)
    }

    private fun cargarResultados(idCampeonato: Int, idPrueba: Int) {
        apiService.getResultadosPorCampeonatoYPrueba(idCampeonato,idPrueba).enqueue(object : Callback<List<Resultado>> {
            override fun onResponse(call: Call<List<Resultado>>, response: Response<List<Resultado>>) {
                if (response.isSuccessful && response.body() != null) {
                    val resultados = response.body()!!
                    if(resultados.isEmpty()){
                        Toast.makeText(this@CompetidorActivity, "No se encontraron competidores inscritos en esta prueba", Toast.LENGTH_SHORT).show()
                        return
                    }
                    resultados.forEach {
                        val m = it.getResultado()
                        resultadosItems.add(m)
                    }

                    competidoresAdapter = CompetidoresAdapterItems(resultadosItems) { it ->
                        sendParameters(it)
                    }
                    binding.competidoresRecyclerView.apply {
                        this.layoutManager = lmanager
                        this.adapter = competidoresAdapter
                    }
                    Toast.makeText(this@CompetidorActivity, "Resultados cargados correctamente", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@CompetidorActivity, "Error en la respuesta", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Resultado>>, t: Throwable) {
                Toast.makeText(this@CompetidorActivity, "Error en la conexión", Toast.LENGTH_SHORT).show()
                Log.e("CampeonatoActivity", "Error: ${t.message}")
                showToastAndRetry(idCampeonato,idPrueba)
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

    private fun sendParameters(resultado: ResultadoDTO) {
        Toast.makeText(this, "Competidor: ${resultado.apellidos.toString().uppercase()}, ${resultado.nombres.toString().uppercase()}", Toast.LENGTH_SHORT).show()
        /*val i = Intent(this, CompetidoresActivity::class.java)
        i.putExtra("idCampeonato", idCampeonato)
        i.putExtra("idPrueba", prueba.id)
        i.putExtra("nombrePrueba", prueba.nombre)
        startActivity(i)*/
    }
}