package com.example.boletimintegral

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.boletimintegral.databinding.FragmentAccessBinding
import com.example.boletimintegral.databinding.ItemResAccessBinding
import com.example.boletimintegral.model.User


class AccessAdapter(
    private val users : MutableList<User>,
    val context : Context,
    val binding:FragmentAccessBinding,
    val edit : (User) -> Unit) : RecyclerView.Adapter<
        AccessAdapter.AccessViewHolder
        >(){

    inner class AccessViewHolder(itemView : ItemResAccessBinding) :
        RecyclerView.ViewHolder(itemView.root){

            private val nameUser : TextView = itemView.tvName
        private val emailUser : TextView = itemView.tvEmail
        private val accessUser :  TextView = itemView.tvAccess

            fun bind(user : User){


                when(user.accessControl){

                    "Bloqueado"-> stateAccessColorBloqueado()

                    "Usuário" -> stateAccessColorUsuario()

                    "Professor" ->stateAccessColorProfessor()

                    "Coordenador" -> stateAccessColorCoordinator()
                }

                itemView.setOnLongClickListener { it->


                    edit(user)

                    true
                }

                nameUser.text = user.name
                emailUser.text = user.email
                accessUser.text = user.accessControl

            }

        private fun stateAccessColorCoordinator(){
            accessUser.backgroundTintList =
                ContextCompat.getColorStateList(
                    context,
                    R.color.black_green
                )

            accessUser.setTextColor(
                ContextCompat.getColorStateList(
                    context,
                    R.color.hiper_light_green
                )
            )
        }


        private fun stateAccessColorProfessor() {
            accessUser.backgroundTintList =
                ContextCompat.getColorStateList(
                    context,
                    R.color.blueBackground
                )

            accessUser.setTextColor(
                ContextCompat.getColorStateList(
                    context,
                    R.color.blueBlack
                )
            )
        }

        private fun stateAccessColorBloqueado() {
            accessUser.backgroundTintList =
                ContextCompat.getColorStateList(
                    context,
                    R.color.redBackground
                )

            accessUser.setTextColor(
                ContextCompat.getColorStateList(
                    context,
                    R.color.black_red
                )
            )
        }

        private fun stateAccessColorUsuario() {
            accessUser.backgroundTintList =
                ContextCompat.getColorStateList(
                    context,
                    R.color.yelowBackground
                )

            accessUser.setTextColor(
                ContextCompat.getColorStateList(
                    context,
                    R.color.yelowBlack
                )
            )
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccessViewHolder =
         AccessViewHolder(ItemResAccessBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false))

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: AccessViewHolder, position: Int) =
       holder.bind(
           users[position]
       )

}