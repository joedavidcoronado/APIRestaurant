package com.example.restaurantesapi.ui.activities.menu

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.restaurantesapi.R
import com.example.restaurantesapi.databinding.ActivityMainBinding
import com.example.restaurantesapi.databinding.ActivityMenuBinding
import com.example.restaurantesapi.ui.viewmodels.MainViewModel
import com.example.restaurantesapi.ui.viewmodels.MenuActivityViewModel

class MenuActivity : AppCompatActivity() {

    lateinit var binding: ActivityMenuBinding
    val model: MenuActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}