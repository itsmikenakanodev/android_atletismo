package com.app.atletismo.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.atletismo.data.entities.campeonatos.CampeonatoResultado
import com.app.atletismo.data.entities.campeonatos.getCampeonatoResultadoDTO
import com.app.atletismo.databinding.ActivityCampeonatoBinding
import com.app.atletismo.logic.CampeonatosLogic
import com.app.atletismo.logic.data.dto.CampeonatoDTO
import com.app.atletismo.ui.adapters.CampeonatosAdapterItems
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate

class CampeonatosListActivity : AppCompatActivity() {
    private lateinit var campeonatosAdapter: CampeonatosAdapterItems
    private var campeonatosItems: MutableList<CampeonatoDTO> = mutableListOf<CampeonatoDTO>()
    private lateinit var binding: ActivityCampeonatoBinding
    private lateinit var lmanager: LinearLayoutManager
    private val campeonatoLogic = CampeonatosLogic()

    private var retryCount = 0
    private val maxRetries = 3 // Número máximo de intentos
    private var retryDelayMillis = 5000L // Retraso inicial en milisegundos (5 segundos)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCampeonatoBinding.inflate(layoutInflater)

        lmanager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

        cargarCampeonatos()

        setContentView(binding.root)

    }

    private fun sendId(item: CampeonatoDTO) {
        val i = Intent(this, CampeonatoDetalleActivity::class.java)
        i.putExtra("idCampeonato", item.id)
        startActivity(i)
    }

    override fun onStart() {
        super.onStart()
    }

    private fun cargarCampeonatos() {
        val fechaActual = LocalDate.now()
        val anio = fechaActual.year
        val mes = fechaActual.monthValue
        campeonatoLogic.obtenerCampeonatosSinPruebas(anio, mes, object : Callback<List<CampeonatoResultado>> {
            override fun onResponse(call: Call<List<CampeonatoResultado>>, response: Response<List<CampeonatoResultado>>) {
                if (response.isSuccessful && response.body() != null) {
                    val campeonatos = response.body()!!

                    campeonatos.forEach {
                        val m = it.getCampeonatoResultadoDTO()
                        campeonatosItems.add(m)
                    }

                    campeonatosAdapter = CampeonatosAdapterItems(campeonatosItems) {
                        sendId(it)
                    }

                    binding.campeonatosRecyclerView.apply {
                        this.adapter = campeonatosAdapter
                        this.layoutManager = lmanager
                    }
                } else {
                    Toast.makeText(this@CampeonatosListActivity, "Error en la respuesta", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<CampeonatoResultado>>, t: Throwable) {
                Toast.makeText(this@CampeonatosListActivity, "Error en la conexión", Toast.LENGTH_SHORT).show()
                Log.e("CampeonatoActivity", "Error: ${t.message}")
                showToastAndRetry()
            }
        })
    }

    private fun showToastAndRetry() {
        if (retryCount < maxRetries) {
            retryCount++
            Toast.makeText(this, "Intentando conectar... intento $retryCount de $maxRetries", Toast.LENGTH_SHORT).show()

            CoroutineScope(Dispatchers.Main).launch {
                delay(retryDelayMillis)
                cargarCampeonatos()
            }
        } else {
            Toast.makeText(this, "No se pudo conectar con el servidor después de varios intentos. Intenta mas tarde", Toast.LENGTH_LONG).show()
        }
    }
}