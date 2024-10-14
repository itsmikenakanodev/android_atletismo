package com.app.atletismo.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.atletismo.R
import com.app.atletismo.databinding.ActivityLoginBinding
import com.app.atletismo.logic.data.Login

class LoginAdapterItems(
    private var fnClick: (Login) -> Unit
) : RecyclerView.Adapter<LoginAdapterItems.LoginViewHolder>() {

    var items: List<Login> = listOf()

    class LoginViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding: ActivityLoginBinding = ActivityLoginBinding.bind(view)

        fun render(
            item: Login,
            fnClick: (Login) -> Unit
        ) {
            // Aquí puedes establecer los valores del `Login` en el layout correspondiente
            // binding.email.text = item.email
            // binding.password.text = item.password

            itemView.setOnClickListener {
                fnClick(item)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LoginViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return LoginViewHolder(
            inflater.inflate(
                R.layout.activity_login,  // Asegúrate de que el layout exista y esté relacionado con `Login`
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: LoginViewHolder, position: Int) {
        holder.render(items[position], fnClick)
    }

    override fun getItemCount(): Int = items.size
}
