package com.example.restaurantesapi.ui.activities.restaurant

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.restaurantesapi.R
import com.example.restaurantesapi.databinding.ActivityRegistroRestaurantBinding
import com.example.restaurantesapi.repositories.objetos.PreferencesRepository
import com.example.restaurantesapi.ui.viewmodels.restaurant.RegistroRestaurantViewModel

class RegistroRestaurantActivity : AppCompatActivity() {
    var token: String = ""
    lateinit var binding: ActivityRegistroRestaurantBinding
    private val model: RegistroRestaurantViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegistroRestaurantBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupViewModelObservers()
        setupEventListeners()
        token = PreferencesRepository.getToken(this@RegistroRestaurantActivity).toString()
    }

    private fun setupEventListeners() {
        binding.btnGuardarRestaurant.setOnClickListener {
            model.saveRestaurant(
                token,
                binding.txtName.editText?.text.toString(),
                binding.txtCAddresss.editText?.text.toString(),
                binding.txtCityyyy.editText?.text.toString(),
                binding.txtDescripcionsss.editText?.text.toString()
            )
        }
    }

    private fun setupViewModelObservers() {
        model.closeActivity.observe(this) {
            if (it) {
                finish()
            }
        }
        model.restaurant.observe(this) {
            if (it == null) {
                return@observe
            }
        }
    }
}