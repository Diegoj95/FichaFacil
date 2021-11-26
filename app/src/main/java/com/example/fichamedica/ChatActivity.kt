package com.example.fichamedica

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fichamedica.adapters.MensajeListAdapter
import com.example.fichamedica.models.AgendaModel
import com.example.fichamedica.models.MensajeModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_sintomas_paciente.*
import java.util.*
import kotlin.collections.ArrayList

class ChatActivity : AppCompatActivity() {

    private var mensajeList: List<MensajeModel> = ArrayList()
    private val mensajeListAdapter = MensajeListAdapter(mensajeList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        // Chat recycler
        recyclerChat.hasFixedSize()
        recyclerChat.layoutManager = LinearLayoutManager(this)
        recyclerChat.adapter = mensajeListAdapter
        devuelveChats()
        chatEnviar.setOnClickListener { enviarMensaje() }
    }

    private fun devuelveChats(){
        val db = FirebaseFirestore.getInstance()

        db.collection("mensaje").
        orderBy("tiempo_exacto", Query.Direction.ASCENDING).get().addOnCompleteListener{
            if(it.isSuccessful){
                mensajeList = it.result!!.toObjects(MensajeModel::class.java)
                mensajeListAdapter.mensajeList = mensajeList.takeLast(10)
                mensajeListAdapter.notifyDataSetChanged()
            }else{
                Log.d(ContentValues.TAG, "Error: ${it.exception!!.message}")
            }
        }
    }

    fun enviarMensaje(){
        val db = FirebaseFirestore.getInstance()

        val síntomas_por_paciente = getMensaje()
        db.collection("mensaje").add(síntomas_por_paciente).addOnSuccessListener {
            devuelveChats()
        }.addOnFailureListener{
            Toast.makeText(this@ChatActivity, "Falló al enviar mensaje", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getMensaje(): MutableMap<String, Any> {
        val chat_data: MutableMap<String, Any> = mutableMapOf()

        chat_data.put("emisor", "Juan Vidal Sanchez")
        chat_data.put("id_emisor", "12")
        chat_data.put("mensaje", mensajeAEnviar.text.toString())


        // Obtener hora actual
        val c: Calendar = Calendar.getInstance()
        c.time = Date()
        c.add(Calendar.HOUR, 1)
        val current_date_plus_hour = c.time
        chat_data.put("tiempo_exacto", current_date_plus_hour)

        return chat_data
    }

}