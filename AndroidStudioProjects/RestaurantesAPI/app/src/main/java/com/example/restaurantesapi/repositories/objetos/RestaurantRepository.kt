package com.example.restaurantesapi.repositories.objetos

import android.content.Context
import com.example.restaurantesapi.api.APIRestaurantService
import com.example.restaurantesapi.models.Filtro
import com.example.restaurantesapi.models.Restaurant
import com.example.restaurantesapi.models.Restaurants
import com.example.restaurantesapi.repositories.RetrofitRepository
import com.example.restaurantesapi.ui.activities.restaurant.RestaurantActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RestaurantRepository {

    fun getRestaurantList(success: (Restaurants?) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()

        val service: APIRestaurantService =
            retrofit.create(APIRestaurantService::class.java)
        service.getRestaurants().enqueue(object : Callback<Restaurants> {
            override fun onResponse(res: Call<Restaurants>, response: Response<Restaurants>) {
                val postList = response.body()
                success(postList)
            }

            override fun onFailure(res: Call<Restaurants>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun insertRestaurant(
        token: String,
        restaurant: Restaurant,
        success: (Restaurant?) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service: APIRestaurantService =
            retrofit.create(APIRestaurantService::class.java)
        service.insertRestaurant( "Bearer $token", restaurant).enqueue(object : Callback<Restaurant> {
            override fun onResponse(res: Call<Restaurant>, response: Response<Restaurant>) {
                val objRestaurant = response.body()
                success(objRestaurant!!)
            }

            override fun onFailure(res: Call<Restaurant>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun getRestaurant(id: Int, success: (Restaurant?) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()

        val service: APIRestaurantService =
            retrofit.create(APIRestaurantService::class.java)
        service.getRestaurantById(id).enqueue(object : Callback<Restaurant?> {
            override fun onResponse(res: Call<Restaurant?>, response: Response<Restaurant?>) {
                success(response.body())
            }

            override fun onFailure(res: Call<Restaurant?>, t: Throwable) {
                failure(t)
            }
        })
    }


    fun getRestaurantByFilter(city: String, success: (Restaurants?) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()

        val service: APIRestaurantService =
            retrofit.create(APIRestaurantService::class.java)
        service.getRestaurantByFilter(city).enqueue(object : Callback<Restaurants> {
            override fun onResponse(res: Call<Restaurants>, response: Response<Restaurants?>) {
                val postList = response.body()
                success(postList)
            }

            override fun onFailure(res: Call<Restaurants>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun getRestaurantByFilterOfTime(city: String, selectedDate: String, selectedTime: String, success: (Restaurants?) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()

        val service: APIRestaurantService =
            retrofit.create(APIRestaurantService::class.java)
        service.getRestaurantByFilterOfTime(Filtro(city, selectedDate,  selectedTime)).enqueue(object : Callback<Restaurants> {
            override fun onResponse(res: Call<Restaurants>, response: Response<Restaurants?>) {
                val postList = response.body()
                success(postList)
            }

            override fun onFailure(res: Call<Restaurants>, t: Throwable) {
                failure(t)
            }
        })
    }





    fun getUserRestaurant(id: Int, success: (Restaurant?) -> Unit, failure: (Throwable) -> Unit){
        val retrofit = RetrofitRepository.getRetrofitInstance()

        val service: APIRestaurantService =
            retrofit.create(APIRestaurantService::class.java)
        service.getRestaurantUser().enqueue(object : Callback<Restaurant?> {
            override fun onResponse(res: Call<Restaurant?>, response: Response<Restaurant?>) {
                success(response.body())
            }

            override fun onFailure(res: Call<Restaurant?>, t: Throwable) {
                failure(t)
            }
        })
    }


    fun updateRestaurant(
        restaurant: Restaurant,
        success: (Restaurant) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()

        val service: APIRestaurantService =
            retrofit.create(APIRestaurantService::class.java)
        val restaurantId = restaurant.id ?: 0
        service.updateRestaurant(restaurant, restaurantId).enqueue(object : Callback<Restaurant> {
            override fun onResponse(res: Call<Restaurant>, response: Response<Restaurant>) {
                val objRestaurant = response.body()!!
                success(objRestaurant)
            }

            override fun onFailure(res: Call<Restaurant>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun deleteRestaurant(
        id: Int,
        success: () -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()

        val service: APIRestaurantService =
            retrofit.create(APIRestaurantService::class.java)
        service.deleteRestaurant(id).enqueue(object : Callback<Void> {
            override fun onResponse(res: Call<Void>, response: Response<Void>) {
                success()
            }

            override fun onFailure(res: Call<Void>, t: Throwable) {
                failure(t)
            }
        })
    }
}