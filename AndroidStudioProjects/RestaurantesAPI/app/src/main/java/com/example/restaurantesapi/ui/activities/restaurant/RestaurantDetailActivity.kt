package com.example.restaurantesapi.ui.activities.restaurant

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.restaurantesapi.R
import com.example.restaurantesapi.databinding.ActivityRestaurantBinding
import com.example.restaurantesapi.databinding.ActivityRestaurantDetailBinding
import com.example.restaurantesapi.models.Restaurant
import com.example.restaurantesapi.ui.adapters.ImageAdapter
import com.example.restaurantesapi.ui.adapters.RestaurantAdapter
import com.example.restaurantesapi.ui.viewmodels.RestaurantDetailViewModel
import com.google.android.material.tabs.TabLayoutMediator

class RestaurantDetailActivity : AppCompatActivity() {
    var id : Int = 0
    var token: String = ""
    lateinit var binding: ActivityRestaurantDetailBinding
    private val model: RestaurantDetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRestaurantDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupEventListeners()
        setupViewModelObservers()

        id = intent.getIntExtra("libroId", -1)
        token = intent.getStringExtra("token").toString()

        if (id != -1) {
            model.loadCategory(id)
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

            var adapter = ImageAdapter(it.photos)
            binding.viewPager.adapter = adapter
            TabLayoutMediator(binding.storyTabs, binding.viewPager) { tab, position -> }.attach()

            binding.apply {
                Glide.with(this@RestaurantDetailActivity)
                    .load(it.logo)
                    .into(imgLogo)
                txtNewName.editText?.setText(it.name)
                txtNewAddres.editText?.setText(it.address)
                txtNewCity.editText?.setText(it.city)
                txtNewDescription.editText?.setText(it.description)
                viewPager.adapter = adapter
            }
        }
    }

    private fun setupEventListeners() {
        binding.btnSaveRestaurant.setOnClickListener {
            model.saveRestaurant(
                token,
                binding.txtNewName.editText?.text.toString(),
                binding.txtNewAddres.editText?.text.toString(),
                binding.txtNewCity.editText?.text.toString(),
                binding.txtNewDescription.editText?.text.toString(),
                id
            )
        }
    }
}