package com.app.atletismo.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.atletismo.R
import com.app.atletismo.databinding.CampeonatoCardBinding
import com.app.atletismo.logic.data.dto.CampeonatoDTO
import java.time.LocalDate

class CampeonatosAdapterItems(
    private var fnClick: (CampeonatoDTO) -> Unit
) : RecyclerView.Adapter<CampeonatosAdapterItems.CampeonatosViewHolder>() {

    var items: List<CampeonatoDTO> = listOf()

    class CampeonatosViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding: CampeonatoCardBinding = CampeonatoCardBinding.bind(view)

        fun render(
            item: CampeonatoDTO,
            fnClick: (CampeonatoDTO) -> Unit
        ) {
            binding.nombreTextView.text = item.nombre
            binding.provinciaTextView.text = item.sede
            binding.organizadorTextView.text = item.organizador
            val fechaActual: LocalDate = LocalDate.now()
            val context = itemView.context
            var estadoActual: String?
            if (fechaActual < item.fechaInicio) {
                estadoActual = "Proximamente"
                binding.estadoTextView.text = estadoActual
                binding.estadoTextView.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.estado_proximo
                    )
                )
            }
            if (item.fechaInicio < fechaActual && item.fechaFin > fechaActual) {
                estadoActual = "En curso"
                binding.estadoTextView.text = estadoActual
                binding.estadoTextView.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.estado_activo
                    )
                )
            } else {
                estadoActual = "Finalizado"
                binding.estadoTextView.text = estadoActual
                binding.estadoTextView.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.estado_finalizado
                    )
                )
                binding.estadoTextView.setTextColor(ContextCompat.getColor(context, R.color.text_color_dark))
            }
            item.estado = estadoActual
            binding.verPruebasButton.setOnClickListener { fnClick(item) }
        }
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
        holder.render(items[position], fnClick)
    }

    override fun getItemCount(): Int = items.size

}