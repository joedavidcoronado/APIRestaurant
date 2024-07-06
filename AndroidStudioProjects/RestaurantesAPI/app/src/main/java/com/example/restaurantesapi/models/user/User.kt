package com.example.restaurantesapi.models.user

data class User (
    var name: String = "",
    val email: String,
    var password: String,
    var phone: String = ""
){
    var id: Int? = null
}