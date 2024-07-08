package com.example.restaurantesapi.ui.activities.reserva


import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.restaurantesapi.R
import com.example.restaurantesapi.databinding.ActivityLlenadoBinding
import com.example.restaurantesapi.repositories.objetos.PreferencesRepository
import com.example.restaurantesapi.ui.viewmodels.reserva.ReservaFormViewModel

class LlenadoActivity : AppCompatActivity() {

    var id: Int= 0
    var token: String = ""
    var name: String = ""

    private lateinit var binding: ActivityLlenadoBinding
    private val model: ReservaFormViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLlenadoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupEventListeners()
        setupViewModelObservers()

        id = intent.getIntExtra("restauranId222", -1)
        token = PreferencesRepository.getToken(this@LlenadoActivity).toString()

    }

    private fun setupViewModelObservers() {
        model.closeActivity.observe(this) {
            if (it) {
                finish()
            }
        }
    }

    private fun setupEventListeners() {
        binding.btnSaveReserva.setOnClickListener {
            val cantidadPersonas = binding.txtPersonasAAA.text.toString()
            val cantidad = cantidadPersonas.toInt()
            model.saveReservaSinComida(
                token,
                id,
                binding.txtFechaAAA.text.toString(),
                binding.txtHoraAAA.text.toString(),
                cantidad
            )
        }

    }
}