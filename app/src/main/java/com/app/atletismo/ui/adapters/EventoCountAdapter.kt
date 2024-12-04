package com.app.atletismo.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.atletismo.databinding.ItemEventoCountBinding
import com.app.atletismo.logic.data.dto.EventoContadorCompetidorDTO

class EventoCountAdapter(private val eventos: List<EventoContadorCompetidorDTO>) :
    RecyclerView.Adapter<EventoCountAdapter.EventoCountViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventoCountViewHolder {
        val binding = ItemEventoCountBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventoCountViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventoCountViewHolder, position: Int) {
        holder.bind(eventos[position])
    }

    override fun getItemCount() = eventos.size

    class EventoCountViewHolder(private val binding: ItemEventoCountBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(evento: EventoContadorCompetidorDTO) {
            binding.eventoNameTextView.text = evento.eventName
            binding.maleCountTextView.text = "Masculinos: ${evento.maleCompetitors}"
            binding.femaleCountTextView.text = "Femeninos: ${evento.femaleCompetitors}"
        }
    }
}