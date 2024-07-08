package com.example.restaurantesapi.ui.viewmodels.restaurant

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restaurantesapi.models.Restaurant
import com.example.restaurantesapi.repositories.objetos.RestaurantRepository

class RegistroRestaurantViewModel: ViewModel() {

    private val _closeActivity: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val closeActivity: LiveData<Boolean> get() = _closeActivity


    private val _restaurant: MutableLiveData<Restaurant?> by lazy {
        MutableLiveData<Restaurant?>(null)
    }
    val restaurant: LiveData<Restaurant?> get() = _restaurant


    fun saveRestaurant(token: String, name: String, address: String, city: String, description: String) {
        val restaurant = Restaurant(
            name = name,
            address = address,
            city = city,
            description = description
        )

        RestaurantRepository.insertRestaurant(token = token ,restaurant,
            success = {
                _closeActivity.value = true
            },
            failure = {
                it.printStackTrace()
            })
    }
}