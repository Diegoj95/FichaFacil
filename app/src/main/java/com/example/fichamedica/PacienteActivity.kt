package com.example.fichamedica

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.fichamedica.SignosPacienteActivity
import com.google.android.material.navigation.NavigationView

class PacienteActivity : AppCompatActivity() {

    private lateinit var fichaBtn:Button
    private lateinit var sintomasBtn:Button
    private lateinit var historialBtn:Button
    private lateinit var signosBtn:Button
    private lateinit var comunicacionBtn:Button
    lateinit var toggle : ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paciente)

        var drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        val navView : NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_ajustes -> Toast.makeText(applicationContext, "Nada que ajustar aún",Toast.LENGTH_SHORT).show()
                R.id.nav_logout -> Toast.makeText(applicationContext, "Imagina que me fui al login y te cerre sesión",Toast.LENGTH_SHORT).show()
                R.id.nav_ficha -> goToFichaPaciente()
                R.id.nav_registros -> goToSintomasPaciente()
                R.id.nav_historial -> goToHistorialMedico()
            }
            true
        }


        fichaBtn = findViewById<Button>(R.id.buttonFichaMedica)
        fichaBtn.setOnClickListener{ goToFichaPaciente() }

        sintomasBtn = findViewById<Button>(R.id.buttonSintomas)
        sintomasBtn.setOnClickListener{ goToSintomasPaciente() }

        historialBtn = findViewById<Button>(R.id.buttonHistorial)
        historialBtn.setOnClickListener{ goToHistorialMedico() }

        signosBtn = findViewById<Button>(R.id.buttonSignos)
        signosBtn.setOnClickListener{ goToSignosPaciente() }

        comunicacionBtn = findViewById<Button>(R.id.buttonComunicacion)
        comunicacionBtn.setOnClickListener{ goToComunicacionPaciente() }



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

    private fun goToSignosPaciente(){
        val intent = Intent(this, SignosPacienteActivity::class.java)
        intent.putExtra("id", "12")
        startActivity(intent)
    }

    private fun goToComunicacionPaciente(){
        val intent = Intent(this, ComunicacionConMedicoMockup::class.java)
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