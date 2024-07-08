package com.example.restaurantesapi.models

typealias Menus = ArrayList<Menu>

data class Menu (
    val id: Int = 0,
    val name: String,
    val restaurant_id: Long,
    val plates: List<Plate>
)
