package com.example.fichamedica.models

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

@IgnoreExtraProperties
class SignosPorPacienteModel {

    @DocumentId
    val documentID: String = ""

    val frecuencia_cardiaca: Int = 1
    val glicemia_capilar: Int = 1
    val presión_arterial: Int = 1
    val saturación_de_oxígeno: Int = 1
    val temperatura: Int = 1
    val id_paciente: String = ""


}