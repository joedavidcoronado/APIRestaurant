package com.example.restaurantesapi.repositories.objetos

import com.example.restaurantesapi.api.APIRestaurantService
import com.example.restaurantesapi.models.Menus
import com.example.restaurantesapi.models.MenuInsert
import com.example.restaurantesapi.models.Plate
import com.example.restaurantesapi.repositories.RetrofitRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object MenuRepository{

    fun insertMenu(
        token: String,
        menuInsert: MenuInsert,
        success: (MenuInsert?) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service: APIRestaurantService = retrofit.create(APIRestaurantService::class.java)

        service.agregarCategoriaAlMenu("Bearer $token", menuInsert).enqueue(object : Callback<MenuInsert> {
            override fun onResponse(call: Call<MenuInsert>, response: Response<MenuInsert>) {
                if (response.isSuccessful) {
                    val objMenu = response.body()
                    success(objMenu)
                } else {
                    failure(Throwable("Error: ${response.code()} ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<MenuInsert>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun getMenu(id: Int, success: (Menus?) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service: APIRestaurantService = retrofit.create(APIRestaurantService::class.java)

        service.getMenuRestaurant(id).enqueue(object : Callback<Menus> {
            override fun onResponse(res: Call<Menus>, response: Response<Menus>) {
                val postList = response.body()
                success(postList)
            }

            override fun onFailure(res: Call<Menus>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun deleteMenuCategory(
        token: String,
        id: Int,
        success: () -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()

        val service: APIRestaurantService =
            retrofit.create(APIRestaurantService::class.java)
        service.deleteCategoriaMenu("Bearer $token", id).enqueue(object : Callback<Void> {
            override fun onResponse(res: Call<Void>, response: Response<Void>) {
                success()
            }
            override fun onFailure(res: Call<Void>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun deletePlato(
        token: String,
        id: String,
        success: () -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()

        val service: APIRestaurantService =
            retrofit.create(APIRestaurantService::class.java)
        service.deletePlato("Bearer $token", id).enqueue(object : Callback<Void> {
            override fun onResponse(res: Call<Void>, response: Response<Void>) {
                success()
            }
            override fun onFailure(res: Call<Void>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun insertPlate(
        token: String,
        plate: Plate,
        success: (Plate?) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service: APIRestaurantService = retrofit.create(APIRestaurantService::class.java)

        service.insertPlato("Bearer $token", plate).enqueue(object : Callback<Plate> {
            override fun onResponse(call: Call<Plate>, response: Response<Plate>) {
                if (response.isSuccessful) {
                    val objMenu = response.body()
                    success(objMenu)
                } else {
                    failure(Throwable("Error: ${response.code()} ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<Plate>, t: Throwable) {
                failure(t)
            }
        })
    }
}