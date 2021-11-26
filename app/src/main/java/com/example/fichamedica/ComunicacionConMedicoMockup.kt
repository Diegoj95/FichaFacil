package com.example.fichamedica

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ComunicacionConMedicoMockup : AppCompatActivity() {

    private lateinit var chatBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comunicacion_con_medico_mockup)

        chatBtn = findViewById<Button>(R.id.abrirMensajeríaButton)
        chatBtn.setOnClickListener{ goToChat() }
    }

    private fun goToChat(){
        val intent = Intent(this, ChatActivity::class.java)
        intent.putExtra("id", "12")
        startActivity(intent)
    }
}

