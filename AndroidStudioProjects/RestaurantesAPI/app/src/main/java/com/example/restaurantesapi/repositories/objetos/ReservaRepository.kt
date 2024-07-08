package com.example.restaurantesapi.repositories.objetos

import android.util.Log
import com.example.restaurantesapi.api.APIRestaurantService
import com.example.restaurantesapi.models.Plate
import com.example.restaurantesapi.models.Reserva
import com.example.restaurantesapi.models.Reservas
import com.example.restaurantesapi.repositories.RetrofitRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ReservaRepository {

    fun getReserva(token: String, success: (Reservas?) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service: APIRestaurantService = retrofit.create(APIRestaurantService::class.java)

        service.getReservaRestaurant("Bearer $token").enqueue(object : Callback<Reservas> {
            override fun onResponse(res: Call<Reservas>, response: Response<Reservas>) {
                val postList = response.body()
                Log.d("ENTRO En la resSERVA", postList.toString())
                success(postList)
            }

            override fun onFailure(res: Call<Reservas>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun insertReserva(
        token: String,
        reserva: Reserva,
        success: (Reserva?) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service: APIRestaurantService = retrofit.create(APIRestaurantService::class.java)

        service.insertReservaSinPlato("Bearer $token", reserva).enqueue(object : Callback<Reserva> {
            override fun onResponse(call: Call<Reserva>, response: Response<Reserva>) {
                if (response.isSuccessful) {
                    val objMenu = response.body()
                    success(objMenu)
                } else {
                    failure(Throwable("Error: ${response.code()} ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<Reserva>, t: Throwable) {
                failure(t)
            }
        })
    }
}