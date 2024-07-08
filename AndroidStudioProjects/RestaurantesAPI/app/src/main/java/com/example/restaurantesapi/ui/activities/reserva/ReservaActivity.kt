package com.example.restaurantesapi.ui.activities.reserva

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurantesapi.R
import com.example.restaurantesapi.databinding.ActivityReservaBinding
import com.example.restaurantesapi.models.Reserva
import com.example.restaurantesapi.models.Reservas
import com.example.restaurantesapi.repositories.objetos.PreferencesRepository
import com.example.restaurantesapi.ui.adapters.ReservaAdapter
import com.example.restaurantesapi.ui.viewmodels.reserva.ReservaViewModel

class ReservaActivity : AppCompatActivity(), ReservaAdapter.OnReservaClickListener {
    lateinit var binding: ActivityReservaBinding
    private val model: ReservaViewModel by viewModels()
    var token: String = ""
    var nombre: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        token = PreferencesRepository.getToken(this@ReservaActivity).toString()
        setupViewModelObservers()
        setupRecyclerView()
        setupViewModelListeners()
    }


    override fun onResume() {
        super.onResume()
        model.fetchReservas(token)
    }

    private fun setupViewModelObservers() {
        model.closeActivity.observe(this) {
            if (it) {
                finish()
            }
        }
        model.reservaList.observe(this) {
            if (it == null) {
                return@observe
            }
        }
    }



    private fun setupViewModelListeners() {
        model.reservaList.observe(this) { menus ->
            val adapter = binding.lstReservas.adapter as ReservaAdapter
            adapter.updateData(menus)
        }
    }


    private fun setupRecyclerView() {
        binding.lstReservas.apply {
            this.adapter = ReservaAdapter(Reservas(), this@ReservaActivity)
            layoutManager = LinearLayoutManager(this@ReservaActivity)
        }
    }

    override fun onReservaClick(menu: Reserva) {

    }

    override fun onReservaDelete(menu: Reserva) {

    }
}