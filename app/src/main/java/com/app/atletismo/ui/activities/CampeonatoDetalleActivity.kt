package com.app.atletismo.ui.activities

import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.app.atletismo.R
import com.app.atletismo.data.entities.campeonatos.CampeonatoConteoGeneral
import com.app.atletismo.data.entities.campeonatos.CompetidorDetalle
import com.app.atletismo.data.entities.campeonatos.EventoContadorCompetidor
import com.app.atletismo.data.entities.campeonatos.getCampeonatoConteoGeneralDTO
import com.app.atletismo.data.entities.campeonatos.getCompetidorDetalleDTO
import com.app.atletismo.data.entities.campeonatos.getEventoContadorCompetidorDTO
import com.app.atletismo.databinding.ActivityCampeonatoDetalleBinding
import com.app.atletismo.logic.ReporteLogic
import com.app.atletismo.logic.data.dto.CampeonatoConteoGeneralDTO
import com.app.atletismo.logic.data.dto.CompetidorDetalleDTO
import com.app.atletismo.logic.data.dto.EventoContadorCompetidorDTO
import com.app.atletismo.ui.adapters.CampeonatoDetallesPagerAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CampeonatoDetalleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCampeonatoDetalleBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    private var generalCount: CampeonatoConteoGeneralDTO? = null
    private var eventoCounts: MutableList<EventoContadorCompetidorDTO> = mutableListOf()
    private var competidoresDetalle: MutableList<CompetidorDetalleDTO> = mutableListOf()
    private val apiService = ReporteLogic().getReporteService()

    //variables para actualizar el progress bar
    private var totalCalls = 3
    private var completedCalls = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCampeonatoDetalleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewPager = binding.viewPager
        tabLayout = binding.tabLayout

        val campeonatoId = intent.getIntExtra("idCampeonato", -1)
        if (campeonatoId == -1) {
            // Manejar el error
            finish()
            return
        }

        showLoading(true)
        loadCampeonatoData(campeonatoId)
    }

    private fun showLoading(show: Boolean) {
        binding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
        binding.progressText.visibility = if (show) View.VISIBLE else View.GONE
        binding.viewPager.visibility = if (show) View.GONE else View.VISIBLE
        binding.tabLayout.visibility = if (show) View.GONE else View.VISIBLE
    }

    private fun updateProgress() {
        completedCalls++
        val progress = (completedCalls.toFloat() / totalCalls.toFloat() * 100).toInt()
        binding.progressBar.progress = progress
        binding.progressText.text = "$progress%"
    }

    private fun loadCampeonatoData(campeonatoId: Int) {
        apiService.obtenerConteoGeneralCampeonato(campeonatoId).enqueue(object :
            Callback<CampeonatoConteoGeneral> {
            override fun onResponse(call: Call<CampeonatoConteoGeneral>, response: Response<CampeonatoConteoGeneral>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    generalCount = data?.getCampeonatoConteoGeneralDTO()
                    updateProgress()
                    checkDataLoaded()
                } else {
                    handleError("No hay competidores inscritos en este campeonato",true)
                }
            }

            override fun onFailure(call: Call<CampeonatoConteoGeneral>, t: Throwable) {
                handleError("Error de red al cargar datos generales")
            }
        })

        apiService.obtenerConteoPruebasCampeonato(campeonatoId).enqueue(object : Callback<List<EventoContadorCompetidor>> {
            override fun onResponse(call: Call<List<EventoContadorCompetidor>>, response: Response<List<EventoContadorCompetidor>>) {
                if (response.isSuccessful) {
                    val data = response.body()!!
                    data.forEach {
                        val item = it.getEventoContadorCompetidorDTO()
                        eventoCounts.add(item)
                    }
                    updateProgress()
                    checkDataLoaded()
                } else {
                    handleError("Error al cargar datos de eventos")
                }
            }

            override fun onFailure(call: Call<List<EventoContadorCompetidor>>, t: Throwable) {
                handleError("Error de red al cargar datos de eventos")
            }
        })

        apiService.obtenerCompetidoresCampeonato(campeonatoId).enqueue(object : Callback<List<CompetidorDetalle>> {
            override fun onResponse(call: Call<List<CompetidorDetalle>>, response: Response<List<CompetidorDetalle>>) {
                if (response.isSuccessful) {
                    val data = response.body()!!
                    data.forEach {
                        val item = it.getCompetidorDetalleDTO()
                        competidoresDetalle.add(item)
                    }
                    updateProgress()
                    checkDataLoaded()
                } else {
                    handleError("Error al cargar detalles de competidores")
                }
            }

            override fun onFailure(call: Call<List<CompetidorDetalle>>, t: Throwable) {
                handleError("Error de red al cargar detalles de competidores")
            }
        })
    }

    private fun checkDataLoaded() {
        if (completedCalls == totalCalls) {
            setupViewPager()
            showLoading(false)
        }
    }

    private fun setupViewPager() {
        val pagerAdapter = CampeonatoDetallesPagerAdapter(this, generalCount, eventoCounts, competidoresDetalle)
        viewPager.adapter = pagerAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "General"
                1 -> "Por Evento"
                else -> competidoresDetalle?.get(position - 2)?.nombreEvento ?: "Evento ${position - 1}"
            }
        }.attach()
    }


    private fun handleError(message: String, finalize: Boolean = false) {
        showLoading(false)

        val snackbar = Snackbar.make(
            binding.root,
            message,
            Snackbar.LENGTH_LONG
        )

        snackbar.setAction("OK") {
            snackbar.dismiss()
            if(finalize) finish()
        }

        // Customize the Snackbar colors
        snackbar.setBackgroundTint(ContextCompat.getColor(this, R.color.error_background))
        snackbar.setTextColor(ContextCompat.getColor(this, R.color.error_text))
        snackbar.setActionTextColor(ContextCompat.getColor(this, R.color.error_action))

        snackbar.show()

        // Log the error for debugging purposes
        Log.e("CampeonatoDetalleActivity", "Error: $message")
    }
}