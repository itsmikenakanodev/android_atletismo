package com.app.atletismo.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.atletismo.R
import com.app.atletismo.logic.data.dto.CampeonatoDTO
import java.time.LocalDate

class CampeonatosAdapterItems(
    private val campeonatos: List<CampeonatoDTO>,
    private var fnClick: (CampeonatoDTO) -> Unit
) : RecyclerView.Adapter<CampeonatosAdapterItems.CampeonatosViewHolder>() {


    class CampeonatosViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombreTextView: TextView = view.findViewById(R.id.nombreTextView)
        val provinciaTextView: TextView = view.findViewById(R.id.provinciaTextView)
        val organizadorTextView: TextView = view.findViewById(R.id.organizadorTextView)
        val estadoTextView: TextView = view.findViewById(R.id.estadoTextView)
        val verPruebasTextView: TextView = view.findViewById(R.id.verPruebasTextView)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CampeonatosViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CampeonatosViewHolder(
            inflater.inflate(
                R.layout.campeonato_card,
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CampeonatosViewHolder, position: Int) {
        val campeonato = campeonatos[position]
        holder.nombreTextView.text = campeonato.nombre
        holder.provinciaTextView.text = campeonato.sede
        holder.organizadorTextView.text = campeonato.organizador
        val fechaActual: LocalDate = LocalDate.now()
        val context = holder.itemView.context
        var estadoActual: String?
        if (fechaActual < campeonato.fechaInicio) {
            estadoActual = "Proximamente"
            holder.estadoTextView.text = estadoActual
            holder.estadoTextView.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.estado_proximo
                )
            )
        }
        else if (campeonato.fechaInicio <= fechaActual && campeonato.fechaFin >= fechaActual) {
            estadoActual = "En curso"
            holder.estadoTextView.text = estadoActual
            holder.estadoTextView.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.estado_activo
                )
            )
        } else {
            estadoActual = "Finalizado"
            holder.estadoTextView.text = estadoActual
            holder.estadoTextView.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.estado_finalizado
                )
            )
            holder.estadoTextView.setTextColor(ContextCompat.getColor(context, R.color.text_color_dark))
        }
        if (campeonato.pruebas.isEmpty()) {
            holder.verPruebasTextView.text = "Ver resultados"
        }
        campeonato.estado = estadoActual
        holder.itemView.setOnClickListener { fnClick(campeonato) }
    }

    override fun getItemCount(): Int = campeonatos.size

}