package com.example.fichamedica.adapters

import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.fichamedica.AtencionMedicoActivity
import com.example.fichamedica.FichaPacienteActivity
import com.example.fichamedica.R
import com.example.fichamedica.SintomasPacienteActivity
import com.example.fichamedica.models.PacienteModel
import com.example.fichamedica.models.SintomasPorPacienteModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.search_single_item.view.*
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.util.*

class SintomasPacienteListAdapter (var sintomasPacienteList: List<SintomasPorPacienteModel>): RecyclerView.Adapter<SintomasPacienteListAdapter.sintomasPacienteListViewHolder>(){

    class sintomasPacienteListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(SintomasPorPacienteModel: SintomasPorPacienteModel){
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm")
            val hora = Date.from(SintomasPorPacienteModel.hora_síntomas.toInstant().minusSeconds(3600))
            val horaSíntomas = sdf.format( hora)
            itemView.single_item_title.text = SintomasPorPacienteModel.descripción_breve + " " + horaSíntomas
            itemView.single_item_title.setOnClickListener {
                val intent = Intent(itemView.single_item_title.context, SintomasPacienteActivity::class.java)
                // TODO: que lleve a una actividad registro síntoma antiguo no modificable
                intent.putExtra("id", SintomasPorPacienteModel.documentID)
                itemView.single_item_title.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): sintomasPacienteListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_single_item, parent, false)
        return sintomasPacienteListViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: sintomasPacienteListViewHolder, position: Int) {
        holder.bind(sintomasPacienteList[position])
    }

    override fun getItemCount(): Int {
        return sintomasPacienteList.size
    }
}