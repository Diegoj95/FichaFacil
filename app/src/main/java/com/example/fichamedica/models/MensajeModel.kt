package com.example.fichamedica.models

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

@IgnoreExtraProperties
class MensajeModel {

    @DocumentId
    val documentID: String = ""
    val emisor: String = ""
    val id_ticket: String = ""
    val mensaje: String = ""
    val tiempo_exacto: Date = Date()

}