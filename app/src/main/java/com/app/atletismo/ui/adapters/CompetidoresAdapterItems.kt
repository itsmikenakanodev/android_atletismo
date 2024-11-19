package com.app.atletismo.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.atletismo.R
import com.app.atletismo.logic.data.dto.ResultadoDTO

class CompetidoresAdapterItems(
    private val resultados: List<ResultadoDTO>,
    private val onItemClick: (ResultadoDTO) -> Unit
) : RecyclerView.Adapter<CompetidoresAdapterItems.CompetidoresViewHolder>() {

    class CompetidoresViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombres: TextView = view.findViewById(R.id.nombreApellidosTextView)
        val numeroCompetidor: TextView = view.findViewById(R.id.numeroCompetidorTextView)
        val categoria: TextView = view.findViewById(R.id.categoriaTextView)
        val estado : TextView = view.findViewById(R.id.estadoCompetidorTextView)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CompetidoresViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.competidor_card, parent, false)
        return CompetidoresViewHolder(view)
    }

    override fun onBindViewHolder(holder: CompetidoresViewHolder, position: Int) {
        val resultado = resultados[position]
        holder.nombres.text = "${resultado.apellidos}, ${resultado.nombres}"
        holder.numeroCompetidor.text = rellenarConCeros(resultado.numeroSocio.toString())
        holder.categoria.text = resultado.categoria
        holder.estado.text = if (resultado.marca != null || resultado.distancia != null || resultado.puntaje != null) "Registrado" else "Pendiente"

        val estadoColor = when (holder.estado.text) {
            "Registrado" -> R.color.estado_registrado
            "Pendiente" -> R.color.estado_pendiente
            else -> R.color.estado_finalizado
        }
        if (estadoColor == R.color.estado_registrado) {
            holder.estado.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.text_color_dark
                )
            )
        }
        holder.estado.setBackgroundColor(
            ContextCompat.getColor(
                holder.itemView.context,
                estadoColor
            )
        )

        holder.itemView.setOnClickListener { onItemClick(resultado) }
    }

    override fun getItemCount(): Int = resultados.size

    private fun rellenarConCeros(numeroCompetidor: String): String {
        val longitud = 6
        val ceros = StringBuilder()
        for (i in 1..(longitud - numeroCompetidor.length)) {
            ceros.append("0")
        }
        return ceros.append(numeroCompetidor).toString()
    }
}

