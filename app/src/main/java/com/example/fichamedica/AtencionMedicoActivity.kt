package com.example.fichamedica

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.fichamedica.models.PacienteModel
import com.firebase.ui.auth.AuthUI
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.collection.LLRBNode
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_atencion_medico.*
import kotlinx.android.synthetic.main.activity_ficha_paciente.*
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.time.Period
import java.time.ZoneId
import java.util.*

class AtencionMedicoActivity : AppCompatActivity() {

    lateinit var toggle : ActionBarDrawerToggle
    private lateinit var editarFichaBtn:Button

    @RequiresApi(Build.VERSION_CODES.O)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_atencion_medico)

        animacionCortinas()


        editarFichaBtn = findViewById<Button>(R.id.editarFichaBoton)
        editarFichaBtn.setOnClickListener{ goToEditarFicha() }

        var fichaAtencion : DrawerLayout = findViewById(R.id.fichaAtencion)
        val navView : NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, fichaAtencion,R.string.open,R.string.close)
        fichaAtencion.addDrawerListener(toggle)
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

        rellenarDatosFicha()



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

    private fun rellenarDatosAtencion(){

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun rellenarDatosFicha(){
        val id_paciente = intent.getStringExtra("identifier")
        val db = FirebaseFirestore.getInstance()
        val query = db.collection("paciente").whereEqualTo("identifier", id_paciente).get()
            .addOnSuccessListener { documents ->
                if(documents.first() != null){
                    val data_paciente = documents.first().toObject(PacienteModel::class.java)
                    editTextNombrePacienteAtencion.setText(data_paciente.name)
                    val sdf = SimpleDateFormat("dd/M/yyyy")
                    val fecha = Date.from(data_paciente.birthDate.toInstant().minusSeconds(3600))
                    val fecha_nacimiento = sdf.format( fecha ).toString()

                    val period: Period = Period.between(fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                    textEdadAtencion.setText(period.years.toString())
                    textFechaNacimientoAtencion.setText(fecha_nacimiento)
                    textSexoPacienteAtencion.setText(data_paciente.sexo)
                    textOcupacionPacienteAtencion.setText(data_paciente.ocupación)
                    textRutPacienteAtencion.setText(data_paciente.rut)


                    val alergias = data_paciente.alergias.toString()
                    textAlergiasPacienteAtencion.setText(alergias.substring(1, alergias.length-1)
                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })

                    val medicamentos = data_paciente.medicamentos.toString()
                    textMedicamentosAtencion.setText(medicamentos.substring(1, medicamentos.length-1)
                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })

                    val comorbidades = data_paciente.antecedentes_mórbidos.toString()
                    textAntecedentesMorbidosAtencion.setText(comorbidades.substring(1, comorbidades.length-1)
                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })

                    val antecedentes_familiares = data_paciente.antecedentes_familiares.toString()
                    textAntecedentesFamilia.setText(antecedentes_familiares.substring(1, antecedentes_familiares.length-1)
                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })
                }
            }
    }


    private fun goToEditarFicha(){
        val intent = Intent(this,EditarFichaActivity::class.java)
        intent.putExtra("id", "12")
        startActivity(intent)
    }

    fun View.visibleOrGone(visible: Boolean) {
        visibility = if(visible) View.VISIBLE else View.GONE
    }

    private fun animacionCortinas(){
        val papiFicha: LinearLayout = findViewById(R.id.linearLayoutPapiFicha)
        val papiAtencionPasada: LinearLayout = findViewById(R.id.linearLayoutPapiAtencionesPasadas)
        val papiSignosSintomas: LinearLayout = findViewById(R.id.linearLayoutPapiSignosSintomas)
        val sintomasRegistrados: RecyclerView = findViewById(R.id.sintomasRegistradosRecycler)
        val signosRegistrados: LinearLayout = findViewById(R.id.linearLayoutSignos)
        papiFicha.setVisibility(View.GONE)
        papiAtencionPasada.setVisibility(View.GONE)
        papiSignosSintomas.setVisibility(View.GONE)
        sintomasRegistrados.setVisibility(View.GONE)
        signosRegistrados.setVisibility(View.GONE)
        fichaMedicaToggle.setOnClickListener {
            if (papiFicha.visibility == View.GONE){
                val animation = AnimationUtils.loadAnimation(this, R.anim.slide_down)
                linearLayoutPapiFicha.startAnimation(animation)
                papiFicha.setVisibility(View.VISIBLE)
                val drawable = ContextCompat.getDrawable(this, R.drawable.eye_icon)
                fichaMedicaToggle.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
                fichaMedicaToggle.background.setTint(ContextCompat.getColor(this, R.color.Blue))
            }else{
                val animation = AnimationUtils.loadAnimation(this, R.anim.slide_up)
                linearLayoutPapiFicha.startAnimation(animation)
                papiFicha.setVisibility(View.GONE)
                val drawable = ContextCompat.getDrawable(this, R.drawable.closed_eye_icon)
                fichaMedicaToggle.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
                fichaMedicaToggle.background.setTint(ContextCompat.getColor(this, R.color.OffGray))
            }
        }

        mostrarSignosToggle.setOnClickListener {
            if (papiSignosSintomas.visibility == View.GONE){
                val animation = AnimationUtils.loadAnimation(this, R.anim.slide_down)
                linearLayoutPapiSignosSintomas.startAnimation(animation)
                papiSignosSintomas.setVisibility(View.VISIBLE)
                val drawable = ContextCompat.getDrawable(this, R.drawable.eye_icon)
                mostrarSignosToggle.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
                mostrarSignosToggle.background.setTint(ContextCompat.getColor(this, R.color.Blue))
            }else{
                val animation = AnimationUtils.loadAnimation(this, R.anim.slide_up)
                linearLayoutPapiSignosSintomas.startAnimation(animation)
                papiSignosSintomas.setVisibility(View.GONE)
                val drawable = ContextCompat.getDrawable(this, R.drawable.closed_eye_icon)
                mostrarSignosToggle.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
                mostrarSignosToggle.background.setTint(ContextCompat.getColor(this, R.color.OffGray))
            }
        }

        historialAtencionesToggle.setOnClickListener {
            if (papiAtencionPasada.visibility == View.GONE){
                val animation = AnimationUtils.loadAnimation(this, R.anim.slide_down)
                linearLayoutPapiAtencionesPasadas.startAnimation(animation)
                papiAtencionPasada.setVisibility(View.VISIBLE)
                val drawable = ContextCompat.getDrawable(this, R.drawable.eye_icon)
                historialAtencionesToggle.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
                historialAtencionesToggle.background.setTint(ContextCompat.getColor(this, R.color.Blue))
            }else{
                val animation = AnimationUtils.loadAnimation(this, R.anim.slide_up)
                linearLayoutPapiAtencionesPasadas.startAnimation(animation)
                papiAtencionPasada.setVisibility(View.GONE)
                val drawable = ContextCompat.getDrawable(this, R.drawable.closed_eye_icon)
                historialAtencionesToggle.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
                historialAtencionesToggle.background.setTint(ContextCompat.getColor(this, R.color.OffGray))
            }
        }

        ojoSintomas.setOnClickListener {
            if (sintomasRegistrados.visibility == View.GONE){
                val animation = AnimationUtils.loadAnimation(this, R.anim.slide_down)
                sintomasRegistradosRecycler.startAnimation(animation)
                sintomasRegistrados.setVisibility(View.VISIBLE)
                ojoSintomas.setImageDrawable(getResources().getDrawable(R.drawable.eye_icon))
                //CODIGO PARA CAMBIAR DRAWABLELEFT A EYE_ICON
            }else{
                val animation = AnimationUtils.loadAnimation(this, R.anim.slide_up)
                sintomasRegistradosRecycler.startAnimation(animation)
                sintomasRegistrados.setVisibility(View.GONE)
                ojoSintomas.setImageDrawable(getResources().getDrawable(R.drawable.closed_eye_icon))
                //CODIGO PARA CAMBIAR DRAWABLELEFT A CLOSED_EYE_ICON
            }
        }

        ojoSignos.setOnClickListener {
            if (signosRegistrados.visibility == View.GONE){
                val animation = AnimationUtils.loadAnimation(this, R.anim.slide_down)
                linearLayoutSignos.startAnimation(animation)
                signosRegistrados.setVisibility(View.VISIBLE)
                ojoSignos.setImageDrawable(getResources().getDrawable(R.drawable.eye_icon))
            }else{
                val animation = AnimationUtils.loadAnimation(this, R.anim.slide_up)
                linearLayoutSignos.startAnimation(animation)
                signosRegistrados.setVisibility(View.GONE)
                ojoSignos.setImageDrawable(getResources().getDrawable(R.drawable.closed_eye_icon))
            }
        }
    }
}