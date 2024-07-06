package com.example.restaurantesapi.repositories

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitRepository {
    fun getRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://restaurantes.jmacboy.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}