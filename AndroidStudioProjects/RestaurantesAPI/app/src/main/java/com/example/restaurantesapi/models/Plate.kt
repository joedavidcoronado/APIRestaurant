package com.example.restaurantesapi.models

typealias Plates = ArrayList<Plate>

data class Plate (
    val name: String,
    val description: String,
    val price: String,
    val menu_category_id: Int
){
    var id: Int? = null
}
