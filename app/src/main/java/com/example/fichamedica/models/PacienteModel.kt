package com.example.fichamedica.models

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

@IgnoreExtraProperties
class PacienteModel {
    @DocumentId
    val documentID: String = ""

    val name: String = ""
    val birthDate: Date = Date()
    val identifier: String = ""
    val name_lc: String = ""
    val apellidos_lc: String = ""
    val maritalStatus: String = ""
    val multipleBirthBoolean: Boolean = false
    val deceasedBoolean: Boolean = false
    val deceasedTime: Date = Date()
    val rut: String = ""

    val alergias: List<String> = ArrayList<String>()
    val antecedentes_mórbidos: List<String> = ArrayList<String>()
    val ocupación: String = ""
    val sexo:String = ""
    val peso:Int = 0
    val enfermedades_crónicas: List<String> = ArrayList<String>()
    val antecedentes_familiares: List<String> = ArrayList<String>()
    val medicamentos: List<String> = ArrayList<String>()


}