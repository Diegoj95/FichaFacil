package com.example.fichamedica.adapters

import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.fichamedica.AtencionMedicoActivity
import com.example.fichamedica.R
import com.example.fichamedica.models.AgendaModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.search_single_item.view.*
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.util.*

class AgendaListAdapter (var agendaList: List<AgendaModel>): RecyclerView.Adapter<AgendaListAdapter.AgendaListViewHolder>(){

    class AgendaListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(agendaModel: AgendaModel){
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm aa")
            val hora = Date.from(agendaModel.fecha.toInstant().minusSeconds(3600))
            val horaAtencion = sdf.format( hora)
            itemView.single_item_title.text = agendaModel.nombre_paciente + " - " + horaAtencion
            itemView.single_item_title.setOnClickListener {
                val intent = Intent(itemView.single_item_title.context, AtencionMedicoActivity::class.java)
                intent.putExtra("identifier", agendaModel.id_paciente)
                intent.putExtra("id_atencion", agendaModel.documentID)
                itemView.single_item_title.context.startActivity(intent)


            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AgendaListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_single_item, parent, false)
        return AgendaListViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: AgendaListViewHolder, position: Int) {
        holder.bind(agendaList[position])
    }

    override fun getItemCount(): Int {
        return agendaList.size
    }
}