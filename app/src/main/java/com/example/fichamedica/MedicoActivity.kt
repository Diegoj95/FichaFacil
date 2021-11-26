package com.example.fichamedica

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fichamedica.adapters.AgendaListAdapter
import com.example.fichamedica.adapters.PacienteListAdapter
import com.example.fichamedica.models.AgendaModel
import com.example.fichamedica.models.PacienteModel
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_medico.*
import java.util.*

class AgendaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
class PacienteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


class MedicoActivity : AppCompatActivity() {

    private lateinit var logoutBtn:Button
    private lateinit var agenda:RecyclerView
    private lateinit var buscador_pacientes:RecyclerView

    private var agendaList: List<AgendaModel> = ArrayList()
    private val agendaListAdapter = AgendaListAdapter(agendaList)
    private var pacienteList: List<PacienteModel> = ArrayList()
    private val pacienteListAdapter = PacienteListAdapter(pacienteList)

    lateinit var toggle : ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medico)

         // Navigation drawer
        var rvAgenda : DrawerLayout = findViewById(R.id.rvAgenda)
        val navView : NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, rvAgenda,R.string.open,R.string.close)
        rvAgenda.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_ajustes -> Toast.makeText(applicationContext, "Nada que ajustar aún",
                    Toast.LENGTH_SHORT).show()

                //new logout button
                R.id.nav_logout -> signOut()
            }
            true
        }

        // Agenda recycler


        agenda = findViewById<RecyclerView>(R.id.agenda)
        agenda.hasFixedSize()
        agenda.layoutManager = LinearLayoutManager(this)
        agenda.adapter = agendaListAdapter
        intent.getStringExtra("identifier")?.let { devuelveAgendas(it) }

        // Buscador recycler
        buscador_pacientes = findViewById<RecyclerView>(R.id.recyclerBuscadorPaciente)
        buscador_pacientes.hasFixedSize()
        buscador_pacientes.layoutManager = LinearLayoutManager(this)
        buscador_pacientes.adapter = pacienteListAdapter

        //intent.getStringExtra("medico")?.let { searchInFirestore(it) }

        // Buscador text
        var timer = Timer()
        var count_text_changes: Int = 0;
        textBuscador.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                timer.cancel()
                // TODO: Loading screen
                timer = Timer()
                timer.schedule(object : TimerTask() {
                    override fun run() {
                        count_text_changes = count_text_changes + 1
                        System.out.println("TEXT CHANGED!!! "+count_text_changes)
                        val searchText: String = textBuscador.text.toString()
                        if(searchText.length > 2){
                            runOnUiThread {
                                searchInFirestore(searchText.lowercase())
                            }
                        }
                    }
                }, 333)
            }
        })
    }

    private fun signOut() {

        Toast.makeText(applicationContext, "Se cerró la sesión", Toast.LENGTH_SHORT).show()
        // [START auth_fui_signout]
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                // ...
            }
        // [END auth_fui_signout]
        val intent = Intent(this,LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun devuelveAgendas(id_medico: String){
        val db = FirebaseFirestore.getInstance()

        db.collection("atención").limit(3).get().addOnCompleteListener{
            if(it.isSuccessful){
                agendaList = it.result!!.toObjects(AgendaModel::class.java)
                agendaListAdapter.agendaList = agendaList.takeLast(3)
                agendaListAdapter.notifyDataSetChanged()
            }else{
                Log.d(ContentValues.TAG, "Error: ${it.exception!!.message}")
            }
        }
    }

    private fun searchInFirestore(searchText: String) {
        val db = FirebaseFirestore.getInstance()

        db.collection("paciente").orderBy("nombres_lc").startAt(searchText).endAt("$searchText\uf8ff")
            .limit(3).get().addOnCompleteListener{
            if(it.isSuccessful){
                pacienteList = it.result!!.toObjects(PacienteModel::class.java)
                pacienteListAdapter.pacienteList = pacienteList.takeLast(3)
                pacienteListAdapter.notifyDataSetChanged()
            }else{
                Log.d(ContentValues.TAG, "Error: ${it.exception!!.message}")
            }
        }

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}