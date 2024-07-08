package com.example.restaurantesapi.ui.viewmodels.reserva

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restaurantesapi.models.Plate
import com.example.restaurantesapi.models.Reserva
import com.example.restaurantesapi.repositories.objetos.MenuRepository
import com.example.restaurantesapi.repositories.objetos.ReservaRepository

class ReservaFormViewModel: ViewModel() {
    private val _closeActivity: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val closeActivity: LiveData<Boolean> get() = _closeActivity


    fun saveReservaSinComida(token: String, restaurant_id: Int, date: String, time: String, people: Int) {
        val reserva = Reserva(
            restaurant_id = restaurant_id,
            date = date,
            time = time,
            people = people
        )

        ReservaRepository.insertReserva(token , reserva,
            success = {
                _closeActivity.value = true
            },
            failure = {
                it.printStackTrace()
            })
    }

    /*fun deletePlato(token: String, id: String) {
        MenuRepository.deletePlato(token , id,
            success = {
                _closeActivity.value = true
            },
            failure = {
                it.printStackTrace()
            })
    }*/
}