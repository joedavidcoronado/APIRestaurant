package com.example.restaurantesapi.ui.activities.restaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurantesapi.R
import com.example.restaurantesapi.databinding.ActivityMisRestaurantsBinding
import com.example.restaurantesapi.databinding.ActivityRestaurantBinding
import com.example.restaurantesapi.models.Restaurant
import com.example.restaurantesapi.models.Restaurants
import com.example.restaurantesapi.repositories.objetos.PreferencesRepository
import com.example.restaurantesapi.repositories.objetos.RestaurantRepository
import com.example.restaurantesapi.ui.activities.reserva.ReservaActivity
import com.example.restaurantesapi.ui.adapters.RestaurantAdapter
import com.example.restaurantesapi.ui.viewmodels.restaurant.OtroViewModel
import com.example.restaurantesapi.ui.viewmodels.restaurant.RestaurantActivityViewModel

class MisRestaurantsActivity : AppCompatActivity(), RestaurantAdapter.OnRestaurantClickListener  {
    lateinit var binding: ActivityMisRestaurantsBinding
    private val model: OtroViewModel by viewModels()
    var token: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMisRestaurantsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupEventListeners()
        setupRecyclerView()
        setupViewModelListeners()
        token = PreferencesRepository.getToken(this@MisRestaurantsActivity).toString()
    }
    override fun onResume() {
        super.onResume()
        model.fetchListaRestaurantsMios(token)
    }

    private fun setupEventListeners() {
        binding.fabReservasRestaurant.setOnClickListener {
            val intent = Intent(this, ReservaActivity::class.java)
            startActivity(intent)
        }
    }



    private fun setupViewModelListeners() {
        model.restaurantList.observe(this) {
            val adapter = (binding.lstRestaurant.adapter as RestaurantAdapter)
            adapter.updateData(it)
        }
    }


    private fun setupRecyclerView() {
        binding.lstRestaurant.apply {
            this.adapter = RestaurantAdapter(Restaurants(), this@MisRestaurantsActivity)
            layoutManager = LinearLayoutManager(this@MisRestaurantsActivity)
        }
    }

    override fun onRestaurantClick(restaurant: Restaurant) {
        val intent = Intent(this, RestaurantDetailActivity::class.java)
        intent.putExtra("restaurantId", restaurant.id)
        intent.putExtra("nombre", restaurant.name)
        startActivity(intent)
    }

    override fun onRestaurantDelete(restaurant: Restaurant) {
        RestaurantRepository.deleteRestaurant(token, restaurant.id!!,
            success = {
                model.fetchListaRestaurantsMios(token)
            },
            failure = {
                it.printStackTrace()
            })
    }
}