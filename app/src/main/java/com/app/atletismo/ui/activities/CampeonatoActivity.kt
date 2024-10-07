package com.app.atletismo.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.atletismo.R
import com.app.atletismo.databinding.ActivityCampeonatoBinding
import com.app.atletismo.logic.CampeonatosLogic
import com.app.atletismo.logic.data.Campeonatos
import com.app.atletismo.ui.adapters.CampeonatosAdapterItems
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CampeonatoActivity : AppCompatActivity() {

    private var campeonatosAdapter: CampeonatosAdapterItems =
        CampeonatosAdapterItems { sendPopMovieItem(it) }
    private var campeonatosItems: MutableList<Campeonatos> = mutableListOf<Campeonatos>()
    private lateinit var binding: ActivityCampeonatoBinding
    private lateinit var lmanager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //binding = ActivityCampeonatoBinding.inflate(layoutInflater)

        lmanager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

        setContentView(binding.root)

    }

    private fun sendPopMovieItem(item: Campeonatos) {
        /*val i = Intent(requireActivity(), DetailsMovieItem::class.java)
        i.putExtra("name", item)
        startActivity(i)*/
    }

    override fun onStart() {
        super.onStart()
        chargeDataRV()
    }

    private fun chargeDataRV() {
        lifecycleScope.launch(Dispatchers.Main) {
            campeonatosItems = withContext(Dispatchers.IO) {
                return@withContext (CampeonatosLogic().getAllCampeonatos())
            }
            campeonatosAdapter.items = campeonatosItems
            binding.rvCampeonatos.apply {
                this.adapter = campeonatosAdapter
                this.layoutManager = lmanager
            }
        }
    }
}