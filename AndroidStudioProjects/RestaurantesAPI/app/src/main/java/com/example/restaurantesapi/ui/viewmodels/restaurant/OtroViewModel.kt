package com.example.restaurantesapi.ui.viewmodels.restaurant

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restaurantesapi.models.Restaurants
import com.example.restaurantesapi.repositories.objetos.RestaurantRepository

class OtroViewModel: ViewModel() {

    private val _restaurantList: MutableLiveData<Restaurants> by lazy {
        MutableLiveData<Restaurants>(Restaurants())
    }
    val restaurantList: LiveData<Restaurants> get() = _restaurantList


    fun fetchListaRestaurantsMios(token: String) {
        RestaurantRepository.getMyRestaurantList(token,
            success = { restaurants ->
                restaurants?.let {

                    _restaurantList.value = it
                }
            },
            failure = {
                it.printStackTrace()
            })
    }
}