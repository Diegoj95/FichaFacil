package com.example.fichamedica

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fichamedica.adapters.SintomasPacienteListAdapter
import com.example.fichamedica.models.AgendaModel
import com.example.fichamedica.models.SintomasPorPacienteModel
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_sintomas_paciente.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SintomasPacienteActivity : AppCompatActivity() {

    lateinit var toggle : ActionBarDrawerToggle
    private lateinit var síntomas_data: MutableMap<String, String>
    private var sintomasPacienteList: List<SintomasPorPacienteModel> = ArrayList()
    private val sintomasPacienteListAdapter = SintomasPacienteListAdapter(sintomasPacienteList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sintomas_paciente)

        var sintomasLayout : DrawerLayout = findViewById(R.id.sintomasLayout)
        val navView : NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, sintomasLayout,R.string.open,R.string.close)
        sintomasLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_ajustes -> Toast.makeText(applicationContext, "Nada que ajustar aún",
                    Toast.LENGTH_SHORT).show()
                R.id.nav_logout -> Toast.makeText(applicationContext, "Imagina que me fui al login y te cerre sesión",
                    Toast.LENGTH_SHORT).show()
                R.id.nav_ficha -> goToFichaPaciente()
                R.id.nav_registros -> Toast.makeText(applicationContext, "Ya estás aquí",
                    Toast.LENGTH_SHORT).show()
                R.id.nav_historial -> goToHistorialMedico()
            }
            true
        }

        // Síntomas previos recycler
        sintomasPreviosRecycler.hasFixedSize()
        sintomasPreviosRecycler.layoutManager = LinearLayoutManager(this)
        sintomasPreviosRecycler.adapter = sintomasPacienteListAdapter
        intent.getStringExtra("id")?.let { devuelveSintomasPrevios(it) }

        buttonIngresarSíntomasPorPaciente.setOnClickListener{
            guardarSintoma()
        }
    }

    fun guardarSintoma(){
        val db = FirebaseFirestore.getInstance()
        val síntomas_por_paciente = getSíntomasPorPaciente()
        db.collection("sintomaPorPaciente").add(síntomas_por_paciente).addOnSuccessListener {
            Toast.makeText(this@SintomasPacienteActivity, "Síntomas registrados correctamente", Toast.LENGTH_SHORT).show()
            intent.getStringExtra("id")?.let { devuelveSintomasPrevios(it) }
        }.addOnFailureListener{
            Toast.makeText(this@SintomasPacienteActivity, "Falló al registrar síntomas", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getSíntomasPorPaciente(): MutableMap<String, Any> {
        val síntomas_data: MutableMap<String, Any> = mutableMapOf()

        intent.getStringExtra("id")?.let { síntomas_data.put("paciente_id", it) }
        síntomas_data.put("descripción", editTextTextMultiLineSíntomasPorPaciente.text.toString())

        // Obtener hora actual
        val c: Calendar = Calendar.getInstance()
        c.time = Date()
        c.add(Calendar.HOUR, 1)
        val current_date_plus_hour = c.time
        síntomas_data.put("hora_síntomas", current_date_plus_hour)

        síntomas_data.put("descripción_breve", editTextBreveSíntomasPorPaciente.text.toString())
        return síntomas_data
    }

    private fun devuelveSintomasPrevios(id_medico: String){
        val db = FirebaseFirestore.getInstance()
        // TODO: Que sea los síntomas del paciente con su id
        db.collection("sintomaPorPaciente").limit(5).
        orderBy("hora_síntomas", Query.Direction.DESCENDING).get().addOnCompleteListener{
            if(it.isSuccessful){
                sintomasPacienteList = it.result!!.toObjects(SintomasPorPacienteModel::class.java)
                sintomasPacienteListAdapter.sintomasPacienteList = sintomasPacienteList.takeLast(5)
                sintomasPacienteListAdapter.notifyDataSetChanged()
            }else{
                Log.d(ContentValues.TAG, "Error: ${it.exception!!.message}")
            }
        }
    }

    private fun goToFichaPaciente(){
        val intent = Intent(this,FichaPacienteActivity::class.java)
        intent.putExtra("id", "12")
        startActivity(intent)
    }

    private fun goToSintomasPaciente(){
        val intent = Intent(this,SintomasPacienteActivity::class.java)
        intent.putExtra("id", "12")
        startActivity(intent)
    }

    private fun goToHistorialMedico(){
        val intent = Intent(this,HistorialPacienteActivity::class.java)
        intent.putExtra("id", "12")
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}