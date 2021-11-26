package com.example.fichamedica

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class HistorialPacienteActivity : AppCompatActivity() {

    lateinit var toggle : ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial_paciente)

        var historialLayout : DrawerLayout = findViewById(R.id.historialLayout)
        val navView : NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, historialLayout,R.string.open,R.string.close)
        historialLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_ajustes -> Toast.makeText(applicationContext, "Nada que ajustar aún",
                    Toast.LENGTH_SHORT).show()
                R.id.nav_logout -> Toast.makeText(applicationContext, "Imagina que me fui al login y te cerre sesión",
                    Toast.LENGTH_SHORT).show()
                R.id.nav_ficha -> goToFichaPaciente()
                R.id.nav_registros -> goToSintomasPaciente()
                R.id.nav_historial -> Toast.makeText(applicationContext, "Ya estás aquí",
                    Toast.LENGTH_SHORT).show()
            }
            true
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
