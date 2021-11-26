package com.example.fichamedica.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.fichamedica.R
import com.example.fichamedica.models.MensajeModel
import kotlinx.android.synthetic.main.message_in.view.*

class MensajeListAdapter (var mensajeList: List<MensajeModel>): RecyclerView.Adapter<MensajeListAdapter.MensajeListViewHolder>(){
    private val VIEW_TYPE_MESSAGE_SENT = 1
    private val VIEW_TYPE_MESSAGE_RECEIVED = 2

    class MensajeListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        @RequiresApi(Build.VERSION_CODES.O)

        fun bind(mensajeModel: MensajeModel){
            //val sdf = SimpleDateFormat("dd/M/yyyy hh:mm aa")
            //val hora = Date.from(mensajeModel.fecha.toInstant().minusSeconds(3600))
            //val horaAtencion = sdf.format( hora)

            itemView.message_in.setText(mensajeModel.mensaje)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MensajeListViewHolder {


        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.message_in, parent, false)
            return MensajeListViewHolder(view)
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {

            val view = LayoutInflater.from(parent.context).inflate(R.layout.message_other, parent, false)
            return MensajeListViewHolder(view)
        }
        val view = LayoutInflater.from(parent.context).inflate(R.layout.message_other, parent, false)
        return MensajeListViewHolder(view)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MensajeListViewHolder, position: Int) {
        holder.bind(mensajeList[position])
    }

    override fun getItemCount(): Int {
        return mensajeList.size
    }

    override fun getItemViewType(position: Int): Int {
        val mensajeModel: MensajeModel = mensajeList.get(position)
        return if (mensajeModel.emisor.equals("Juan Vidal Sanchez")) {
            // If the current user is the sender of the message
            VIEW_TYPE_MESSAGE_SENT
        } else {
            // If some other user sent the message
            VIEW_TYPE_MESSAGE_RECEIVED
        }
    }
}