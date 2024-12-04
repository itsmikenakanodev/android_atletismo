package com.app.atletismo.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.atletismo.R
import com.app.atletismo.databinding.FragmentCompetidoresBinding
import com.app.atletismo.logic.data.dto.CompetidorDetalleDTO
import com.app.atletismo.ui.adapters.CompetidoresAdapter

class CompetidoresFragment : Fragment() {
    private var _binding: FragmentCompetidoresBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCompetidoresBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelableArrayList<CompetidorDetalleDTO>("competidores")?.let { competidores ->
            binding.recyclerView.layoutManager = LinearLayoutManager(context)
            binding.recyclerView.adapter = CompetidoresAdapter(competidores)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(eventoName: String, competidores: List<CompetidorDetalleDTO>?) = CompetidoresFragment().apply {
            arguments = Bundle().apply {
                putString("eventoName", eventoName)
                putParcelableArrayList("competidores", ArrayList(competidores ?: emptyList()))
            }
        }
    }
}