package com.example.restaurantesapi.api

import com.example.restaurantesapi.models.Filtro
import com.example.restaurantesapi.models.Menu
import com.example.restaurantesapi.models.Plate
import com.example.restaurantesapi.models.Reservation
import com.example.restaurantesapi.models.Reservations
import com.example.restaurantesapi.models.Restaurant
import com.example.restaurantesapi.models.Restaurants
import com.example.restaurantesapi.models.user.LoginRequestDTO
import com.example.restaurantesapi.models.user.LoginResponseDTO
import com.example.restaurantesapi.models.user.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
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

    //Filtro ciudad
    @GET("api/restaurants/search")
    fun getRestaurantByFilter(
        @Body city: String
    ): Call<Restaurants>

    //filtro fecha y hora
    @GET("api/restaurants/search")
    fun getRestaurantByFilterOfTime(
        @Body cualquierCosa: Filtro
    ): Call<Restaurants>

    //restaurants de un usuario
    @GET("api/restaurants")
    fun getRestaurantUser(): Call<Restaurant>

    //restaurant por ID
    @GET("api/restaurants/{id}")
    fun getRestaurantById(
        @Path("id") id: Int
    ): Call<Restaurant?>

    //insertar
    @POST("restaurants")
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
        @Path("id") id: Int
    ): Call<Void>



    /// PARA MI MENU /////////////////////////////////////////////////////////////////////////////////


    // obtener menu de restaurant
    @GET("api/restaurants/{id}/menu")
    fun getMenuRestaurant(
        @Path("id") id: Int
    ): Call<Menu?>

    // agregar categoria del menu
    @POST("api/menu-categories")
    //"name": "Postres",
    //"restaurant_id": "1"
    fun agregarCategoriaAlMenu(
        @Body name: String,
        @Body restaurant_id: Int
    ): Call<Restaurant>

    // actualizar categoria del menu
    @PUT("api/menu-categories/{id}")
    fun updateCategoriaMenu(
        @Body menu: Menu,
        @Path ("id") id: Int
    ): Call<Menu>

    // eliminar categoria del restaurant
    @DELETE("api/menu-categories/{id}")
    fun deleteCategoriaDeRestaurant(
        @Path("id") id: Int
    ): Call<Void>

    // agregar plato
    @POST("api/plates")
    fun insertPlato(
        @Body plate: Plate
    ): Call<Plate>

    // actualizar plato
    @PUT("api/plates/{id}")
    fun updatePlato(
        @Body plate: Plate,
        @Path("id") id: Int
    ): Call<Plate>

    // eliminar plato
    @DELETE("api/plates/{id}")
    fun deletePlato(
        @Path("id") id: Int
    ): Call<Void>


    /// PARA MI RESERVA //////////////////////////////////////////////////////////////////////////////

    // agregar reserva sin plato
    @POST("api/reservations")
    fun insertReservaSinPlato(
        @Body reservation: Reservation
    ): Call<Reservation>

    // agregar reserva con plato
    @POST("api/reservations")
    fun insertReservaConPlato(
        @Body reservation: Reservation
    ): Call<Reservation>

    // obtener lista de reservas
    @GET("api/reservations")
    fun getReservas(
    ): Call<Reservations>

    // obtener una reserva
    @GET("api/reservations/{id}")
    fun getReservationById(
        @Body reservation: Reservation,
        @Path("id") id: Int
    ): Call<Reservation?>

    // obtener reservas de un restaurant
    @GET("api/restaurants/{id}/reservations")
    fun getReservasDeUnRestaurant(
        @Path("id") id: Int
    ): Call<Reservations>
}