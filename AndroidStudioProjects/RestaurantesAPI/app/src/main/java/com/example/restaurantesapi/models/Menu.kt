package com.example.restaurantesapi.models

typealias Menus = ArrayList<Menu>

data class Menu (
    val id: Long,
    val name: String,
    val restaurantid: Long,
    val plates: List<Plate>
)
