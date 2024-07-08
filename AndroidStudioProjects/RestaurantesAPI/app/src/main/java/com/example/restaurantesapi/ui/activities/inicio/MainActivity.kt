package com.example.restaurantesapi.ui.activities.inicio

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.restaurantesapi.R
import com.example.restaurantesapi.databinding.ActivityMainBinding
import com.example.restaurantesapi.repositories.objetos.PreferencesRepository
import com.example.restaurantesapi.ui.activities.restaurant.RestaurantActivity
import com.example.restaurantesapi.ui.viewmodels.MainViewModel

class MainActivity : AppCompatActivity(){
        lateinit var binding: ActivityMainBinding
    val model: MainViewModel by viewModels()
    var ok: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        checkToken()
        setupEventListeners()
        setupViewModelObservers()
    }

    private fun setupViewModelObservers() {
        model.errorMessage.observe(this) {
            if (it.isNotEmpty()) {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                ok = false
            }
        }
    }

    private fun checkToken() {
        val token = PreferencesRepository.getToken(this@MainActivity)
        if (token != null) {
            val token = PreferencesRepository.getToken(this@MainActivity)
            val intent = Intent(this, RestaurantActivity::class.java)
            intent.putExtra("token", token)
            startActivity(intent)
        }
    }


    private fun setupEventListeners() {
        binding.btnIniciarSesion.setOnClickListener {
            val email = binding.txtCorreo.editText?.text.toString()
            val password = binding.txtContrasena.editText?.text.toString()
            if(email == "" || email.trim() == "" || password == "" || password.trim() == ""){
                Toast.makeText(this, "Contraseña/Usuario vacio", Toast.LENGTH_SHORT).show()
            }else{
                model.login(email, password, this)
                if (!ok){
                    val token = PreferencesRepository.getToken(this@MainActivity)
                    val intent = Intent(this, RestaurantActivity::class.java)
                    startActivity(intent)
                }
            }

        }

        binding.btnRegistro.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }
        binding.btnVisita.setOnClickListener{
            Toast.makeText(this, "Entrando sin Usuario", Toast.LENGTH_SHORT).show()
            clearToken()
            Handler().postDelayed({
                val intent = Intent(this, RestaurantActivity::class.java)
                startActivity(intent)
                finish()
            }, 2000)
        }
    }
    private fun clearToken() {
        PreferencesRepository.clearToken(this@MainActivity)
    }
}