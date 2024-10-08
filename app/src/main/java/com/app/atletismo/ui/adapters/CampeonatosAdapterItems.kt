package com.app.atletismo.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.atletismo.R
import com.app.atletismo.databinding.CampeonatoCardBinding
import com.app.atletismo.logic.CampeonatosLogic
import com.app.atletismo.logic.data.Campeonatos

class CampeonatosAdapterItems(
    private var fnClick: (Campeonatos) -> Unit
) : RecyclerView.Adapter<CampeonatosAdapterItems.CampeonatosViewHolder>() {

    var items: List<Campeonatos> = listOf()

    class CampeonatosViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding: CampeonatoCardBinding = CampeonatoCardBinding.bind(view)


        fun render(
            item: Campeonatos,
            fnClick: (Campeonatos) -> Unit
        ) {
            binding.title.text= item.title;

            /*itemView.setOnClickListener {
                fnClick(item)
            }*/
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