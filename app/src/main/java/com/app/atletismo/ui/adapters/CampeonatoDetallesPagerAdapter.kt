package com.app.atletismo.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.atletismo.data.entities.campeonatos.CampeonatoConteoGeneral
import com.app.atletismo.logic.data.dto.CampeonatoConteoGeneralDTO
import com.app.atletismo.logic.data.dto.CompetidorDetalleDTO
import com.app.atletismo.logic.data.dto.EventoContadorCompetidorDTO
import com.app.atletismo.ui.fragments.CompetidoresFragment
import com.app.atletismo.ui.fragments.EventoCountFragment
import com.app.atletismo.ui.fragments.GeneralCountFragment

class CampeonatoDetallesPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val generalCount: CampeonatoConteoGeneralDTO?,
    private val eventoCounts: List<EventoContadorCompetidorDTO>?,
    private val competidoresDetalle: List<CompetidorDetalleDTO>?
) : FragmentStateAdapter(fragmentActivity) {

    private val eventoNames = competidoresDetalle?.map { it.nombreEvento }?.distinct() ?: emptyList()

    override fun getItemCount(): Int = 2 + eventoNames.size

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> GeneralCountFragment.newInstance(generalCount)
            1 -> EventoCountFragment.newInstance(eventoCounts)
            else -> {
                val eventoName = eventoNames[position - 2]
                val competidores = competidoresDetalle?.filter { it.nombreEvento == eventoName }
                CompetidoresFragment.newInstance(eventoName, competidores)
            }
        }
    }
}