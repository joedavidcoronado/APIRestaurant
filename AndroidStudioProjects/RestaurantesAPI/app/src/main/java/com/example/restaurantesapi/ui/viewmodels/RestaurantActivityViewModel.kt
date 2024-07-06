package com.example.restaurantesapi.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restaurantesapi.models.Restaurants
import com.example.restaurantesapi.repositories.objetos.RestaurantRepository


class RestaurantActivityViewModel : ViewModel() {
    private val _restaurantList: MutableLiveData<Restaurants> by lazy {
        MutableLiveData<Restaurants>(Restaurants())
    }
    val restaurantList: LiveData<Restaurants> get() = _restaurantList


    fun fetchListaRestaurants() {
        RestaurantRepository.getRestaurantList(
            success = { restaurants ->
                restaurants?.let {

                    _restaurantList.value = it
                }
            },
            failure = {
                it.printStackTrace()
            })
    }

    fun fetchListaFiltroCity(city: String) {
        RestaurantRepository.getRestaurantByFilter(
            city = city,
            success = { restaurants ->
                restaurants?.let {
                    _restaurantList.value = it
                }
            },
            failure = {
                it.printStackTrace()
            })
    }
    fun fetchListaFiltroTime(city: String, selectedDate: String, selectedTime: String) {
        RestaurantRepository.getRestaurantByFilterOfTime(
            city = city,
            selectedDate = selectedDate,
            selectedTime = selectedTime,
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