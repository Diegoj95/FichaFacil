package com.example.fichamedica

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.fichamedica.models.PacienteModel
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_atencion_medico.*
import kotlinx.android.synthetic.main.activity_ficha_paciente.*
import java.text.SimpleDateFormat
import java.time.Period
import java.time.ZoneId
import java.util.*

class FichaPacienteActivity : AppCompatActivity() {

    lateinit var toggle : ActionBarDrawerToggle

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ficha_paciente)

        var fichaLayout : DrawerLayout = findViewById(R.id.fichaLayout)
        val navView : NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, fichaLayout,R.string.open,R.string.close)
        fichaLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_ajustes -> Toast.makeText(applicationContext, "Nada que ajustar aún",
                    Toast.LENGTH_SHORT).show()
                R.id.nav_logout -> Toast.makeText(applicationContext, "Imagina que me fui al login y te cerre sesión",
                    Toast.LENGTH_SHORT).show()
                R.id.nav_ficha -> Toast.makeText(applicationContext, "Ya estás aquí",
                    Toast.LENGTH_SHORT).show()
                R.id.nav_registros -> goToSintomasPaciente()
                R.id.nav_historial -> goToHistorialMedico()
            }
            true
        }


        val id_paciente = intent.getStringExtra("id")

        val db = FirebaseFirestore.getInstance()
        val query = db.collection("paciente").whereEqualTo("identifier", id_paciente).get()
            .addOnSuccessListener { documents ->
                if(documents.first() != null){
                    val data_paciente = documents.first().toObject(PacienteModel::class.java)
                    editTextNombrePaciente.setText(data_paciente.name)
                    val sdf = SimpleDateFormat("dd/M/yyyy")
                    val fecha = Date.from(data_paciente.birthDate.toInstant().minusSeconds(3600))
                    val fecha_nacimiento = sdf.format( fecha ).toString()
                    val period: Period = Period.between(fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), Date().toInstant().atZone(
                        ZoneId.systemDefault()).toLocalDate())
                    textEdad.setText(period.years.toString())

                    textFechaNacimiento.setText(fecha_nacimiento)
                    textSexoPaciente.setText(data_paciente.sexo)
                    textOcupacion.setText(data_paciente.ocupación)
                    textRutPaciente.setText(data_paciente.rut)

                    val alergias = data_paciente.alergias.toString()
                    textAlergias.setText(alergias.substring(1, alergias.length-1)
                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })


                    val medicamentos = data_paciente.medicamentos.toString()
                    textMedicamentos.setText(medicamentos.substring(1, medicamentos.length-1)
                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })

                    val comorbidades = data_paciente.antecedentes_mórbidos.toString()
                    textAntecedentesMorbidos.setText(comorbidades.substring(1, comorbidades.length-1)
                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })

                    val antecedentes_familiares = data_paciente.antecedentes_familiares.toString()
                    textAntecedentesFamiliaPaciente.setText(antecedentes_familiares.substring(1, antecedentes_familiares.length-1)
                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })

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
