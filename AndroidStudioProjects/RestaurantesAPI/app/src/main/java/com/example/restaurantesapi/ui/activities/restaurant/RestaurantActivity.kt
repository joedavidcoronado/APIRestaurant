package com.example.restaurantesapi.ui.activities.restaurant

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurantesapi.R
import com.example.restaurantesapi.databinding.ActivityRestaurantBinding
import com.example.restaurantesapi.models.Restaurant
import com.example.restaurantesapi.models.Restaurants
import com.example.restaurantesapi.repositories.objetos.RestaurantRepository
import com.example.restaurantesapi.ui.adapters.RestaurantAdapter
import com.example.restaurantesapi.ui.viewmodels.RestaurantActivityViewModel

class RestaurantActivity  : AppCompatActivity(), RestaurantAdapter.OnRestaurantClickListener {
    lateinit var binding: ActivityRestaurantBinding
    private val model: RestaurantActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupEventListeners()
        setupRecyclerView()
        setupViewModelListeners()
    }


    override fun onResume() {
        super.onResume()
        model.fetchListaRestaurants()
    }

    private fun setupEventListeners() {
        binding.fabAddRestaurant.setOnClickListener {
            //val intent = Intent(this, RestaurantDetailActivity::class.java)
            //startActivity(intent)
        }
        binding.fabGoTo.setOnClickListener {
            //val intent = Intent(this, GeneroActivity::class.java)
            //startActivity(intent)
        }
        binding.btnBuscar.setOnClickListener {
            var city = binding.txtCity.text.toString()
            var selectedDate = binding.txtDate.toString()
            var selectedTime = binding.txtHora.toString()

            if(city.isNotEmpty() && selectedDate.isNotEmpty() && selectedTime.isNotEmpty()){
                model.fetchListaFiltroTime(city, selectedDate, selectedTime)
            }else if(city.isEmpty() && selectedDate.isNotEmpty() && selectedTime.isNotEmpty()){
                Toast.makeText(this, "Te faltó la ciudad mi rey", Toast.LENGTH_SHORT).show()
            }else if(city.isEmpty() && selectedDate.isEmpty() && selectedTime.isNotEmpty()){
                Toast.makeText(this, "faltan ciudad y fecha", Toast.LENGTH_SHORT).show()
            }else if(city.isNotEmpty() && selectedDate.isEmpty() && selectedTime.isEmpty()){
                model.fetchListaFiltroCity(city)
            }else if(city.isEmpty() && selectedDate.isEmpty() && selectedTime.isEmpty())
                Toast.makeText(this, "cara e charco", Toast.LENGTH_SHORT).show()
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
            this.adapter = RestaurantAdapter(Restaurants(), this@RestaurantActivity)
            layoutManager = LinearLayoutManager(this@RestaurantActivity)
        }
    }

    override fun onRestaurantClick(restaurant: Restaurant) {
        val intent = Intent(this, RestaurantDetailActivity::class.java)
        intent.putExtra("restaurantId", restaurant.id)
        startActivity(intent)
    }

    override fun onRestaurantDelete(restaurant: Restaurant) {
        RestaurantRepository.deleteRestaurant(restaurant.id!!,
            success = {
                model.fetchListaRestaurants()
            },
            failure = {
                it.printStackTrace()
            })
    }
}