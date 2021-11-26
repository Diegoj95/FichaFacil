package com.example.fichamedica

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_atencion_medico.*
import kotlinx.android.synthetic.main.activity_signos_paciente.*
import kotlinx.android.synthetic.main.activity_sintomas_paciente.*
import java.util.*

class SignosPacienteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signos_paciente)

        cortinas()
        registrarSignosBtn.setOnClickListener {
            guardarSintoma()
        }


    }

    fun guardarSintoma(){
        val db = FirebaseFirestore.getInstance()
        val signos_por_paciente = getSignosPorPaciente()
        db.collection("signosPorPaciente").add(signos_por_paciente).addOnSuccessListener {
            Toast.makeText(this@SignosPacienteActivity, "Signos registrados correctamente", Toast.LENGTH_SHORT).show()

        }.addOnFailureListener{
            Toast.makeText(this@SignosPacienteActivity, "Falló al registrar signos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getSignosPorPaciente(): MutableMap<String, Any> {
        val signos_data: MutableMap<String, Any> = mutableMapOf()

        intent.getStringExtra("id")?.let { signos_data.put("paciente_id", it) }
        if(registrarTemperatura.text.toString().length>0){ signos_data.put("temperatura", registrarTemperatura.text.toString().toInt()) }
        if(registrarFrecuenciaCardiaca.text.toString().length>0){ signos_data.put("frecuencia_cardiaca", registrarFrecuenciaCardiaca.text.toString().toInt()) }
        if(registrarPresionDiastolica.text.toString().length>0){ signos_data.put("presión_diastólica", registrarPresionDiastolica.text.toString().toInt()) }
        if(registrarPresionSistolica.text.toString().length>0){ signos_data.put("presión_sistólica", registrarPresionSistolica.text.toString().toInt()) }
        if(registrarSaturacionOxigeno.text.toString().length>0){ signos_data.put("saturación_de_oxígeno", registrarSaturacionOxigeno.text.toString().toInt()) }
        if(registrarGlicemia.text.toString().length>0){ signos_data.put("glicemia_capilar", registrarGlicemia.text.toString().toInt()) }

        // Obtener hora actual
        val c: Calendar = Calendar.getInstance()
        c.time = Date()
        c.add(Calendar.HOUR, 1)
        val current_date_plus_hour = c.time
        signos_data.put("hora_signos", current_date_plus_hour)

        return signos_data
    }

    private fun verificarSiPusoAlgo(data: String, texto: EditText){
        if(texto.text.toString().length>0){

        }
    }

    private fun cortinas(){
        val temperatura: EditText = findViewById(R.id.registrarTemperatura)
        val glicemia: EditText = findViewById(R.id.registrarGlicemia)
        val presion: LinearLayout = findViewById(R.id.linearLayoutRegistrarPresion)
        val frecuenciaCardiaca: EditText = findViewById(R.id.registrarFrecuenciaCardiaca)
        val saturacionOxigeno: EditText = findViewById(R.id.registrarSaturacionOxigeno)

        temperatura.setVisibility(View.GONE)
        glicemia.setVisibility(View.GONE)
        presion.setVisibility(View.GONE)
        frecuenciaCardiaca.setVisibility(View.GONE)
        saturacionOxigeno.setVisibility(View.GONE)

        ojoTemperatura.setOnClickListener {
            if (temperatura.visibility == View.GONE){
                val animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
                temperatura.startAnimation(animation)
                temperatura.setVisibility(View.VISIBLE)
                ojoTemperatura.setImageDrawable(getResources().getDrawable(R.drawable.eye_icon))
            }else{
                temperatura.setVisibility(View.GONE)
                ojoTemperatura.setImageDrawable(getResources().getDrawable(R.drawable.closed_eye_icon))
            }
        }

        ojoGlicemia.setOnClickListener {
            if (glicemia.visibility == View.GONE){
                val animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
                glicemia.startAnimation(animation)
                glicemia.setVisibility(View.VISIBLE)
                ojoGlicemia.setImageDrawable(getResources().getDrawable(R.drawable.eye_icon))
            }else{
                glicemia.setVisibility(View.GONE)
                ojoGlicemia.setImageDrawable(getResources().getDrawable(R.drawable.closed_eye_icon))
            }
        }

        ojoPresionArterial.setOnClickListener {
            if (presion.visibility == View.GONE){
                val animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
                presion.startAnimation(animation)
                presion.setVisibility(View.VISIBLE)
                ojoPresionArterial.setImageDrawable(getResources().getDrawable(R.drawable.eye_icon))
            }else{
                presion.setVisibility(View.GONE)
                ojoPresionArterial.setImageDrawable(getResources().getDrawable(R.drawable.closed_eye_icon))
            }
        }

        ojoFrecuenciaCardiaca.setOnClickListener {
            if (frecuenciaCardiaca.visibility == View.GONE){
                val animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
                frecuenciaCardiaca.startAnimation(animation)
                frecuenciaCardiaca.setVisibility(View.VISIBLE)
                ojoFrecuenciaCardiaca.setImageDrawable(getResources().getDrawable(R.drawable.eye_icon))
            }else{
                frecuenciaCardiaca.setVisibility(View.GONE)
                ojoFrecuenciaCardiaca.setImageDrawable(getResources().getDrawable(R.drawable.closed_eye_icon))
            }
        }

        ojoSaturacionOxigeno.setOnClickListener {
            if (saturacionOxigeno.visibility == View.GONE){
                val animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
                saturacionOxigeno.startAnimation(animation)
                saturacionOxigeno.setVisibility(View.VISIBLE)
                ojoSaturacionOxigeno.setImageDrawable(getResources().getDrawable(R.drawable.eye_icon))
            }else{
                saturacionOxigeno.setVisibility(View.GONE)
                ojoSaturacionOxigeno.setImageDrawable(getResources().getDrawable(R.drawable.closed_eye_icon))
            }
        }
    }
}