package com.example.restaurantesapi.ui.viewmodels.menu

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restaurantesapi.models.MenuInsert
import com.example.restaurantesapi.models.Plate
import com.example.restaurantesapi.repositories.objetos.MenuRepository

class PlatoViewModel: ViewModel() {

    private val _closeActivity: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val closeActivity: LiveData<Boolean> get() = _closeActivity


    fun savePlato(token: String, name: String, description: String, price: String, menu_category_id: Int) {
        val plate = Plate(
            name = name,
            description = description,
            price = price,
            menu_category_id = menu_category_id
        )
        Log.d("El IDE DE LA CATEGORIA ES ", plate.menu_category_id.toString())
        MenuRepository.insertPlate(token ,plate,
            success = {
                _closeActivity.value = true
            },
            failure = {
                it.printStackTrace()
            })
    }

    fun deletePlato(token: String, id: String) {
        MenuRepository.deletePlato(token , id,
            success = {
                _closeActivity.value = true
            },
            failure = {
                it.printStackTrace()
            })
    }
}