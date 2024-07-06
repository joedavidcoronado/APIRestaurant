package com.example.restaurantesapi.ui.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restaurantesapi.repositories.objetos.AutenticationRepository
import com.example.restaurantesapi.repositories.objetos.PreferencesRepository

class MainViewModel : ViewModel() {

    private val _errorMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }
    val errorMessage: LiveData<String> get() = _errorMessage


    fun login(email: String, password: String, context: Context) {
        AutenticationRepository.inicioSesion(email,
            password,
            success = {
                if(it == null) {
                    _errorMessage.value = "Usuario o contraseña incorrectos"
                    return@inicioSesion
                }
                _errorMessage.value = ""
                Log.d("MainViewModel", "Token: ${it.access_token}")
                val token: String = it.access_token!!
                PreferencesRepository.saveToken(token, context)
            }, failure = {
                it.printStackTrace()
            })
    }

}