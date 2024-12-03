package com.app.atletismo.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.atletismo.R
import com.app.atletismo.databinding.FragmentEventoCountBinding
import com.app.atletismo.logic.data.dto.EventoContadorCompetidorDTO
import com.app.atletismo.ui.adapters.EventoCountAdapter

class EventoCountFragment : Fragment() {
    private var _binding: FragmentEventoCountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentEventoCountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelableArrayList<EventoContadorCompetidorDTO>("eventoCounts")?.let { counts ->
            binding.recyclerView.layoutManager = LinearLayoutManager(context)
            binding.recyclerView.adapter = EventoCountAdapter(counts)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(eventoCounts: List<EventoContadorCompetidorDTO>?) = EventoCountFragment().apply {
            arguments = Bundle().apply {
                putParcelableArrayList("eventoCounts", ArrayList(eventoCounts ?: emptyList()))
            }
        }
    }
}