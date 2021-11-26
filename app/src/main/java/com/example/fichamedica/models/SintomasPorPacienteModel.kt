package com.example.fichamedica.models

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

@IgnoreExtraProperties
class SintomasPorPacienteModel {

    @DocumentId
    val documentID: String = ""

    val descripcion: String = ""
    val hora_síntomas: Date = Date()
    val paciente_id: String = ""
    val descripción_breve: String = ""

}