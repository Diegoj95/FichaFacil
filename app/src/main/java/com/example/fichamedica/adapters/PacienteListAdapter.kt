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
import com.example.fichamedica.models.PacienteModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.search_single_item.view.*
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.util.*

class PacienteListAdapter (var pacienteList: List<PacienteModel>): RecyclerView.Adapter<PacienteListAdapter.pacienteListViewHolder>(){

    class pacienteListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(pacienteModel: PacienteModel){
            itemView.single_item_title.text = pacienteModel.name
            itemView.single_item_title.setOnClickListener {
                val intent = Intent(itemView.single_item_title.context, FichaPacienteActivity::class.java)
                intent.putExtra("identifier", pacienteModel.identifier)
                itemView.single_item_title.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): pacienteListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_single_item, parent, false)
        return pacienteListViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: pacienteListViewHolder, position: Int) {
        holder.bind(pacienteList[position])
    }

    override fun getItemCount(): Int {
        return pacienteList.size
    }
}