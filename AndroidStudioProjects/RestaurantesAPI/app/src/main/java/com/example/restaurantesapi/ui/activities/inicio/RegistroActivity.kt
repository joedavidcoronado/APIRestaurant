package com.example.restaurantesapi.ui.activities.inicio

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.restaurantesapi.R
import com.example.restaurantesapi.databinding.ActivityRegistroBinding
import com.example.restaurantesapi.ui.activities.restaurant.RestaurantActivity
import com.example.restaurantesapi.ui.viewmodels.RegistroViewModel

class RegistroActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegistroBinding
    private val model: RegistroViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupViewModelObservers()
        setupEvenListener()
    }

    private fun setupViewModelObservers() {
        model.closeActivity.observe(this) {
            if (it) {
                finish()
            }
        }
        model.user.observe(this) {
            if (it == null) {
                return@observe
            }
            binding.txtRegistroNombre.editText?.setText(it.name)
            binding.txtRegistroCorreo.editText?.setText(it.email)
            binding.txtRegistroContraseA.editText?.setText(it.password)
            binding.txtRegistroTelefono.editText?.setText(it.phone)
        }
    }

    private fun setupEvenListener() {
        binding.btnSaveUser.setOnClickListener {
            model.saveUser(
                binding.txtRegistroNombre.editText?.text.toString(),
                binding.txtRegistroCorreo.editText?.text.toString(),
                binding.txtRegistroContraseA.editText?.text.toString(),
                binding.txtRegistroTelefono.editText?.text.toString()
            )
            val intent = Intent(this, RestaurantActivity::class.java)
            startActivity(intent)
        }
    }
}