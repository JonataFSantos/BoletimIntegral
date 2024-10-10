package com.example.boletimintegral.model

import java.io.Serializable


data class User(
    val email : String = "",
    val name : String= "",
    val lastName: String ="",
    val accessMessage : AccessMessage = AccessMessage(),
    val phone : String = "",
    var accessControl : String = "Bloqueado",
    val childrensData : MutableList<DataStudent> = mutableListOf()
)  : Serializable