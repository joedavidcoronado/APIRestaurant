package com.example.restaurantesapi.ui.activities.menu

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.restaurantesapi.R
import com.example.restaurantesapi.databinding.ActivityMenuBinding
import com.example.restaurantesapi.databinding.ActivityPlatoBinding
import com.example.restaurantesapi.databinding.ActivityRestaurantBinding
import com.example.restaurantesapi.models.Plate
import com.example.restaurantesapi.repositories.objetos.PreferencesRepository
import com.example.restaurantesapi.ui.viewmodels.menu.MenuViewModel
import com.example.restaurantesapi.ui.viewmodels.menu.PlatoViewModel

class PlatoActivity : AppCompatActivity() {
    lateinit var binding: ActivityPlatoBinding
    private val model: PlatoViewModel by viewModels()
    var token: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPlatoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupViewModelObservers()
        setupEventListeners()
        token = PreferencesRepository.getToken(this@PlatoActivity).toString()
    }

    private fun setupEventListeners() {
        binding.btninsertarPlato.setOnClickListener {
            val idPlatoText = binding.txtIdCat.editText?.text.toString()
                val idPlato = idPlatoText.toInt()
                model.savePlato(
                    token,
                    binding.txtNombre.editText?.text.toString(),
                    binding.txtDescripcionPlato.editText?.text.toString(),
                    binding.txtPrecio.editText?.text.toString(),
                    idPlato
                )
                Log.d("NO ES El ACTIVITY", " ")
        }
        binding.btnEliminar.setOnClickListener {
            model.deletePlato(
                token,
                binding.txtIdPlato.editText?.text.toString()
            )
        }

    }

    private fun setupViewModelObservers() {
        model.closeActivity.observe(this) {
            if (it) {
                finish()
            }
        }
    }
}