package com.example.boletimintegral

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.boletimintegral.databinding.ItemResMessageBinding
import com.example.boletimintegral.model.Message

class MessageAdapter(private val message : MutableList<Message>):
    RecyclerView.Adapter<
            MessageAdapter.MessageViewHolder
            >()
{

    inner class MessageViewHolder(itemView: ItemResMessageBinding) :
    RecyclerView.ViewHolder(itemView.root){

        val tvTitleMessage : TextView = itemView.tvNameWorkShop
        val tvMessageText : TextView = itemView.tvMessage
        val tvDateText : TextView = itemView.tvDate



        fun bind(titleMessage : String,
                 message: String,
                 hour : String,
                 date : String){


            tvTitleMessage.text = titleMessage
            tvMessageText.text = message

            val time = "$hour - $date"

            tvDateText.text = time



        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val itemInflate = ItemResMessageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return MessageViewHolder(itemInflate)
    }

    override fun getItemCount(): Int = message.size


    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(
            titleMessage = message[position].titleMessage,
            message = message[position].message,
            hour = message[position].instantHour,
            date = message[position].instantDate


        )
    }

}