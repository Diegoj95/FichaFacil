package com.example.fichamedica

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.fichamedica.models.PacienteModel
import com.firebase.ui.auth.AuthUI
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_atencion_medico.*
import kotlinx.android.synthetic.main.activity_editar_ficha.*
import java.text.SimpleDateFormat
import java.util.*

class EditarFichaActivity : AppCompatActivity() {

    lateinit var toggle : ActionBarDrawerToggle
    private lateinit var logoutBtn: Button
    @RequiresApi(Build.VERSION_CODES.O)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_ficha)

        var editarFichaPaciente : DrawerLayout = findViewById(R.id.editarFichaPaciente)
        val navView : NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, editarFichaPaciente,R.string.open,R.string.close)
        editarFichaPaciente.addDrawerListener(toggle)
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

        val id_paciente = intent.getStringExtra("id")

        val db = FirebaseFirestore.getInstance()
        val query = db.collection("paciente").whereEqualTo("id", id_paciente).get()
            .addOnSuccessListener { documents ->
                if(documents.first() != null){
                    val data_paciente = documents.first().toObject(PacienteModel::class.java)
                    editarFichaNombrePaciente.setText(data_paciente.name)
                    val sdf = SimpleDateFormat("dd/M/yyyy")
                    val fecha = Date.from(data_paciente.birthDate.toInstant().minusSeconds(3600))
                    val fecha_nacimiento = sdf.format( fecha ).toString()

                    editarFichaFechaNacimiento.setText(fecha_nacimiento)
                    editarFichaSexoPaciente.setText(data_paciente.sexo)
                    editarFichaOcupacion.setText(data_paciente.ocupación)

                    val alergias = data_paciente.alergias.toString()
                    editarFichaAlergias.setText(alergias.substring(1, alergias.length-1)
                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })


                    val medicamentos = data_paciente.medicamentos.toString()
                    editarFichaMedicamentos.setText(medicamentos.substring(1, medicamentos.length-1)
                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })

                    val comorbidades = data_paciente.antecedentes_mórbidos.toString()
                    editarFichaAntecedentesMorbidos.setText(comorbidades.substring(1, comorbidades.length-1)
                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })

                    val enfermedades_cronicas = data_paciente.enfermedades_crónicas.toString()
                    editarFichaEnfermedadesCronicas.setText(enfermedades_cronicas.substring(1, enfermedades_cronicas.length-1)
                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })
                }
            }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
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
}