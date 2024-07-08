package com.example.restaurantesapi.models

import com.example.restaurantesapi.models.user.User


typealias Restaurants = ArrayList<Restaurant>

data class Restaurant (
    val name: String,
    val address: String = "",
    val city: String? = "",
    val description: String = "",
    val user: User? = null,
    var photos: List<Photo> = emptyList()
){
    var id: Int? = null
    var logo: String? = null
}