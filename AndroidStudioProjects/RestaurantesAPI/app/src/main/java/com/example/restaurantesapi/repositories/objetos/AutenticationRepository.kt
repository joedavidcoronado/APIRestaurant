package com.example.restaurantesapi.repositories.objetos

import com.example.restaurantesapi.api.APIRestaurantService
import com.example.restaurantesapi.models.user.LoginRequestDTO
import com.example.restaurantesapi.models.user.LoginResponseDTO
import com.example.restaurantesapi.models.user.User
import com.example.restaurantesapi.repositories.RetrofitRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object AutenticationRepository {
    
    fun callUser(
        user: User,
        success: (User?) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()

        val service: APIRestaurantService =
            retrofit.create(APIRestaurantService::class.java)
        service.saveUser(user).enqueue(object : Callback<User> {
            override fun onResponse(res: Call<User>, response: Response<User>) {
                val objUser = response.body()!!
                success(objUser)
            }

            override fun onFailure(res: Call<User>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun inicioSesion(
        email: String,
        password: String,
        success: (LoginResponseDTO?) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()

        val service: APIRestaurantService =
            retrofit.create(APIRestaurantService::class.java)
        service.logIn(LoginRequestDTO(email, password)).enqueue(object : Callback<LoginResponseDTO> {
            override fun onResponse(res: Call<LoginResponseDTO>, response: Response<LoginResponseDTO>) {
                if (response.code() == 401) {
                    success(null)
                    return
                }
                val loginResponse = response.body()
                success(loginResponse)
            }

            override fun onFailure(res: Call<LoginResponseDTO>, t: Throwable) {
                failure(t)
            }
        })
    }
}