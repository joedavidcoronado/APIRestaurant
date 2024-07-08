package com.example.restaurantesapi.models

import com.example.restaurantesapi.models.user.User

typealias Reservas = ArrayList<Reserva>

data class Reserva (
    val restaurant_id: Int,
    val date: String,
    val time: String,
    val people: Int,
){
    val id: Int? = null
    //val userid: Int? = null
    val status: String? = null
    val user: User? = null
    val restaurant: Restaurant? = null
    //var food: List<Plate> = emptyList()
}
