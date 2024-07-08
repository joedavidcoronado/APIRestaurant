package com.example.restaurantesapi.ui.viewmodels.reserva

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restaurantesapi.models.Reservas
import com.example.restaurantesapi.repositories.objetos.ReservaRepository

class ReservaViewModel: ViewModel() {
    private val _reservaList: MutableLiveData<Reservas> by lazy {
        MutableLiveData<Reservas>(Reservas())
    }
    val reservaList: LiveData<Reservas> get() = _reservaList

    private val _closeActivity: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val closeActivity: LiveData<Boolean> get() = _closeActivity

    fun fetchReservas(token: String) {
        ReservaRepository.getReserva(
            token,
            success = { reservas ->
                reservas?.let {
                    _reservaList.value = it
                }
            },
            failure = {
                it.printStackTrace()
            })
    }

    /*fun addCategory(token: String, name: String, id: String) {
        val reservaInsert = reservaInsert(
            name = name,
            restaurant_id = id
        )

        ReservaRepository.insertReserva(token ,reservaInsert,
            success = {
                _closeActivity.value = true
            },
            failure = {
                it.printStackTrace()
            })
    }*/
}