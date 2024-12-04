package com.app.atletismo.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.atletismo.R
import com.app.atletismo.data.entities.campeonatos.CampeonatoConteoGeneral
import com.app.atletismo.databinding.FragmentGeneralCountBinding
import com.app.atletismo.logic.data.dto.CampeonatoConteoGeneralDTO

class GeneralCountFragment : Fragment() {
    private var _binding: FragmentGeneralCountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentGeneralCountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<CampeonatoConteoGeneralDTO>("generalCount")?.let { count ->
            binding.campeonatoNameTextView.text = count.championshipName
            binding.totalCompetidoresTextView.text = "Total: ${count.maleCompetitors + count.femaleCompetitors}"
            binding.maleCompetidoresTextView.text = "Masculinos: ${count.maleCompetitors}"
            binding.femaleCompetidoresTextView.text = "Femeninos: ${count.femaleCompetitors}"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(generalCount: CampeonatoConteoGeneralDTO?) = GeneralCountFragment().apply {
            arguments = Bundle().apply {
                putParcelable("generalCount", generalCount)
            }
        }
    }
}