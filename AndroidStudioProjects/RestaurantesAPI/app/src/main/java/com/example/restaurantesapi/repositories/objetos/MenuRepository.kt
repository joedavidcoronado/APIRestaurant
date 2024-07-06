package com.example.restaurantesapi.repositories.objetos

import com.example.restaurantesapi.api.APIRestaurantService
import com.example.restaurantesapi.models.Menu
import com.example.restaurantesapi.models.Menus
import com.example.restaurantesapi.repositories.RetrofitRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object MenuRepository{
    
    /*fun insertMenu(
        menu: Menu,
        success: (Menu?) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()

        val service: APILibreriaService =
            retrofit.create(APILibreriaService::class.java)
        service.insertMenu(menu).enqueue(object : Callback<Menu> {
            override fun onResponse(res: Call<Menu>, response: Response<Menu>) {
                val objMenu = response.body()
                success(objMenu!!)
            }

            override fun onFailure(res: Call<Menu>, t: Throwable) {
                failure(t)
            }
        })
    }*/

    fun getMenu(id: Int, success: (Menu?) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()

        val service: APIRestaurantService =
            retrofit.create(APIRestaurantService::class.java)
        service.getMenuRestaurant(id).enqueue(object : Callback<Menu?> {
            override fun onResponse(res: Call<Menu?>, response: Response<Menu?>) {
                success(response.body())
            }

            override fun onFailure(res: Call<Menu?>, t: Throwable) {
                failure(t)
            }
        })
    }


    /*fun updateMenu(
        menu: Menu,
        success: (Menu) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()

        val service: APIRestaurantService =
            retrofit.create(APIRestaurantService::class.java)
        val menuId = menu.id?: 0
        service.updateCategoriaMenu().enqueue(object : Callback<Menu> {
            override fun onResponse(res: Call<Menu>, response: Response<Menu>) {
                val objMenu = response.body()!!
                success(objMenu)
            }

            override fun onFailure(res: Call<Menu>, t: Throwable) {
                failure(t)
            }
        })
    }*/

    fun deleteMenu(
        id: Int,
        success: () -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()

        val service: APIRestaurantService =
            retrofit.create(APIRestaurantService::class.java)
        service.deletePlato(id).enqueue(object : Callback<Void> {
            override fun onResponse(res: Call<Void>, response: Response<Void>) {
                success()
            }
            override fun onFailure(res: Call<Void>, t: Throwable) {
                failure(t)
            }
        })
    }
}