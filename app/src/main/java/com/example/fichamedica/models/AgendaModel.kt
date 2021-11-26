package com.example.fichamedica.models

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

@IgnoreExtraProperties
class AgendaModel {

    @DocumentId
    val documentID: String = ""

    val nombre_medico: String = ""
    val nombre_paciente: String = ""
    val id_medico: String = ""
    val id_paciente: String = ""
    val fecha: Date = Date()

}