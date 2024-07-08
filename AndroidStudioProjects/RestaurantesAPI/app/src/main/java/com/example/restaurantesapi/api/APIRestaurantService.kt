package com.example.restaurantesapi.api

import com.example.restaurantesapi.models.Filtro
import com.example.restaurantesapi.models.Menu
import com.example.restaurantesapi.models.Menus
import com.example.restaurantesapi.models.Plate
import com.example.restaurantesapi.models.Reservas
import com.example.restaurantesapi.models.Restaurant
import com.example.restaurantesapi.models.Restaurants
import com.example.restaurantesapi.models.user.LoginRequestDTO
import com.example.restaurantesapi.models.user.LoginResponseDTO
import com.example.restaurantesapi.models.MenuInsert
import com.example.restaurantesapi.models.Reserva
import com.example.restaurantesapi.models.user.User
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path


interface APIRestaurantService {


    /// USUARIOS PERROOO ////////////////////////////////////////////////////////////////////////////

    // registro de usuarios
    @POST("api/registeruser")
    fun saveUser(
        @Body user: User
    ): Call<User>

    // inicio de sesion
    @POST("api/loginuser")
    fun logIn(@Body loginRequest: LoginRequestDTO): Call<LoginResponseDTO>


    /// PARA MI RESTAURANT ///////////////////////////////////////////////////////////////////////////

    //sin filtro
    @POST("api/restaurants/search")
        fun getRestaurants(): Call<Restaurants>

    //mis restaurants
    @GET("/api/restaurants/")
    fun getMyRestaurants(
        @Header("Authorization") token:String
    ): Call<Restaurants>


    //Filtro ciudad
    @POST("api/restaurants/search")
    fun getRestaurantByFilter(
        @Body city: String
    ): Call<Restaurants>

    //filtro fecha y hora
    @POST("api/restaurants/search")
    fun getRestaurantByFilterOfTime(
        @Body cualquierCosa: Filtro
    ): Call<Restaurants>


    //restaurant por ID
    @GET("api/restaurants/{id}")
    fun getRestaurantById(
        @Path("id") id: Int
    ): Call<Restaurant?>

    //insertar
    @POST("api/restaurants")
    fun insertRestaurant(
        @Header("Authorization") token:String,
        @Body restaurant: Restaurant
    ): Call<Restaurant>

    // UPDATE
    @PUT("api/restaurants/{id}")
    fun updateRestaurant(
        @Body restaurant: Restaurant,
        @Path("id") id: Int
    ): Call<Restaurant>

    // DELETE
    @DELETE("api/restaurants/{id}")
    fun deleteRestaurant(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Call<Void>

    @Multipart
    @POST("api/restaurants/{id}/logo")
    fun subirLogo(
        @Path("id") id: Int,
        @Part logo: MultipartBody.Part
    ): Call<Void>



    /// PARA MI MENU /////////////////////////////////////////////////////////////////////////////////


    // obtener menu de restaurant
    @GET("api/restaurants/{id}/menu")
    fun getMenuRestaurant(
        @Path("id") id: Int
    ): Call<Menus>

    // agregar categoria del menu
    @POST("api/menu-categories")
    fun agregarCategoriaAlMenu(
        @Header("Authorization") token: String,
        @Body menu: MenuInsert
    ): Call<MenuInsert>

    // eliminar plato
    @DELETE("api/menu-categories/{id}")
    fun deleteCategoriaMenu(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Call<Void>

    // agregar plato
    @POST("api/plates")
    fun insertPlato(
        @Header("Authorization") token: String,
        @Body plate: Plate
    ): Call<Plate>

    // eliminar plato
    @DELETE("api/plates/{id}")
    fun deletePlato(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Call<Void>


    /// PARA MI RESERVA //////////////////////////////////////////////////////////////////////////////

    // agregar reserva sin plato
    @POST("api/reservations")
    fun insertReservaSinPlato(
        @Header("Authorization") token: String,
        @Body reserva: Reserva
    ): Call<Reserva>

    // agregar reserva con plato
    @GET("api/reservations")
    fun insertReservaConPlato(
        @Body reservation: Reservas
    ): Call<Reservas>

    // obtener lista de reservas
    @GET("api/reservations")
    fun getReservaRestaurant(
        @Header("Authorization") token: String,
    ): Call<Reservas>

    // obtener una reserva
    @GET("api/reservations/{id}")
    fun getReservasById(
        @Body reservation: Reservas,
        @Path("id") id: Int
    ): Call<Reservas?>

    // obtener reservas de un restaurant
    @GET("api/restaurants/{id}/reservations")
    fun getReservasDeUnRestaurant(
        @Path("id") id: Int
    ): Call<Reservas>
}