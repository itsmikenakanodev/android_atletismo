package com.app.atletismo.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.atletismo.databinding.ActivityPruebasBinding
import com.app.atletismo.logic.data.dto.PruebaDTO
import com.app.atletismo.ui.adapters.PruebasAdapterItems

class PruebasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPruebasBinding
    private lateinit var lmanager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPruebasBinding.inflate(layoutInflater)

        lmanager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

        val pruebas: ArrayList<PruebaDTO>? = intent.getParcelableArrayListExtra("pruebas")
        val nombreCampeonato = intent.getStringExtra("nombreCampeonato")!!
        val estado = intent.getStringExtra("estado")
        val idCampeonato = intent.getIntExtra("idCampeonato", 0)

        pruebas!!.forEach {
            it.estado = estado
        }

        binding.campeonatoTitleTextView.text = nombreCampeonato;

        if (pruebas.size == 0) {
            Toast.makeText(this, "No hay pruebas disponibles", Toast.LENGTH_SHORT).show()
        } else {
            val pruebasAdapter = PruebasAdapterItems(idCampeonato, pruebas) { id, prueba ->
                sendParameters(id, prueba)
            }
            binding.pruebasRecyclerView.apply {
                this.layoutManager = lmanager
                this.adapter = pruebasAdapter
            }
        }


        setContentView(binding.root)

    }

    private fun sendParameters(idCampeonato: Int, prueba: PruebaDTO) {
        val i = Intent(this, CompetidorActivity::class.java)
        i.putExtra("idCampeonato", idCampeonato)
        i.putExtra("idPrueba", prueba.id)
        i.putExtra("nombrePrueba", prueba.nombre)
        startActivity(i)
    }

}