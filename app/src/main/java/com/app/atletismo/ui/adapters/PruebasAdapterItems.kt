package com.app.atletismo.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.atletismo.R
import com.app.atletismo.logic.data.dto.PruebaDTO

class PruebasAdapterItems(
    private val idCampeonato: Int,
    private val pruebas: List<PruebaDTO>,
    private val onItemClick: (Int, PruebaDTO) -> Unit
) : RecyclerView.Adapter<PruebasAdapterItems.PruebasViewHolder>() {

    class PruebasViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombrePruebaTextView: TextView = view.findViewById(R.id.nombrePruebaTextView)
        val tipoPruebaTextView: TextView = view.findViewById(R.id.tipoPruebaTextView)
        val estadoPruebaTextView: TextView = view.findViewById(R.id.estadoPruebaTextView)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PruebasViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.prueba_card, parent, false)
        return PruebasViewHolder(view)
    }

    override fun onBindViewHolder(holder: PruebasViewHolder, position: Int) {
        val prueba = pruebas[position]
        holder.nombrePruebaTextView.text = prueba.nombre
        holder.tipoPruebaTextView.text = "Tipo: ${prueba.tipo}"
        holder.estadoPruebaTextView.text = prueba.estado

        val estadoColor = when (prueba.estado) {
            "En progreso" -> R.color.estado_activo
            "Proximamente" -> R.color.estado_proximo
            else -> R.color.estado_finalizado
        }
        if (estadoColor == R.color.estado_finalizado) {
            holder.estadoPruebaTextView.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.text_color_dark
                )
            )
        }
        holder.estadoPruebaTextView.setBackgroundColor(
            ContextCompat.getColor(
                holder.itemView.context,
                estadoColor
            )
        )

        holder.itemView.setOnClickListener { onItemClick(idCampeonato, prueba) }
    }

    override fun getItemCount(): Int = pruebas.size

}
