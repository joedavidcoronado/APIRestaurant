package com.example.restaurantesapi.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restaurantesapi.models.user.User
import com.example.restaurantesapi.repositories.objetos.AutenticationRepository

class RegistroViewModel : ViewModel() {
    private val _closeActivity: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val closeActivity: LiveData<Boolean> get() = _closeActivity
    private val _user: MutableLiveData<User?> by lazy {
        MutableLiveData<User?>(null)
    }
    val user: LiveData<User?> get() = _user


    fun saveUser(name: String, email: String, password: String, phone: String) {
        val user = User(
            name = name,
            email = email,
            password = password,
            phone = phone
        )

        AutenticationRepository.callUser(user,
            success = {
                _closeActivity.value = true
                      },
            failure = {
                it.printStackTrace()
            })
    }
}