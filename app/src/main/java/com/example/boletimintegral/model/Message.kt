package com.example.boletimintegral.model

import kotlinx.datetime.*
import java.io.Serializable
import java.time.temporal.WeekFields.ISO


data class Message(
    val titleMessage: String = "",
    val message : String = "",
    val id : String = Clock.toString(),
    val accessMessage : AccessMessage = AccessMessage()
){

    val instantHour: String = "00:00:00"
    val instantDate: String = "00/00/00"

}
