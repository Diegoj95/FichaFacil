package com.example.fichamedica.models

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

@IgnoreExtraProperties
class MedicoModel {

    @DocumentId
    val documentID: String = ""

    val identifier: String = ""
    val name: String = ""
    val address: String = ""
    val birthDate: Date = Date()

    val id_paciente: String = ""
    val fecha: Date = Date()

}