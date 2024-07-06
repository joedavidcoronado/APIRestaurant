package com.example.restaurantesapi.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restaurantesapi.models.Restaurant
import com.example.restaurantesapi.repositories.objetos.RestaurantRepository

class RestaurantDetailViewModel : ViewModel() {

    private val _closeActivity: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val closeActivity: LiveData<Boolean> get() = _closeActivity


    private val _restaurant: MutableLiveData<Restaurant?> by lazy {
        MutableLiveData<Restaurant?>(null)
    }
    val restaurant: LiveData<Restaurant?> get() = _restaurant


    fun saveRestaurant(token: String, name: String, address: String, city: String, description: String, id: Int) {
        val restaurant = Restaurant(
            name = name,
            address = address,
            city = city,
            description = description
        )

        if(id == -1){
            RestaurantRepository.insertRestaurant(token = token ,restaurant,
                success = {
                    _closeActivity.value = true
                },
                failure = {
                    it.printStackTrace()
                })
        }
        else if (id != 0) {
            restaurant.id = id
            RestaurantRepository.updateRestaurant(restaurant,
                success = {
                    _closeActivity.value = true
                },
                failure = {
                    it.printStackTrace()
                })
        }
    }

    fun loadCategory(id: Int) {
        RestaurantRepository.getRestaurant(id,
            success = {
                _restaurant.value = it
            },
            failure = {
                it.printStackTrace()
            })
    }
}