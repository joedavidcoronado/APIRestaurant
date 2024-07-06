package com.example.restaurantesapi.models

typealias Photos  = ArrayList<Plate>

data class Photo (
    val url: String
){
    var id: Int? = null
}
