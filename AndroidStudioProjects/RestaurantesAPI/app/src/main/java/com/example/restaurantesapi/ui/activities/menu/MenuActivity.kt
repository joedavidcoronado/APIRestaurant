package com.example.restaurantesapi.ui.activities.menu

import ListaDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurantesapi.R
import com.example.restaurantesapi.databinding.ActivityMenuBinding
import com.example.restaurantesapi.models.Menu
import com.example.restaurantesapi.models.Menus
import com.example.restaurantesapi.repositories.objetos.MenuRepository
import com.example.restaurantesapi.repositories.objetos.PreferencesRepository
import com.example.restaurantesapi.ui.activities.restaurant.RestaurantDetailActivity
import com.example.restaurantesapi.ui.adapters.MenuAdapter
import com.example.restaurantesapi.ui.viewmodels.menu.MenuViewModel


class MenuActivity  : AppCompatActivity(), MenuAdapter.OnMenuClickListener {
    lateinit var binding: ActivityMenuBinding
    private val model: MenuViewModel by viewModels()
    var token: String = ""
    var id: Int = 0
    var nombre: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        token = PreferencesRepository.getToken(this@MenuActivity).toString()
        id = intent.getIntExtra("restaurantId2", -1)
        nombre = intent.getStringExtra("restaurantName").toString()

        setupViewModelObservers()
        setupRecyclerView()
        setupViewModelListeners()
    }


    override fun onResume() {
        super.onResume()
        model.fetchMenus(id)
    }

    private fun setupViewModelObservers() {
        model.closeActivity.observe(this) {
            if (it) {
                finish()
            }
        }
        model.menuList.observe(this) {
            if (it == null) {
                return@observe
            }
        }
    }



    private fun setupViewModelListeners() {
        model.menuList.observe(this) { menus ->
            val adapter = binding.lstMenu.adapter as MenuAdapter
            adapter.updateData(menus)
            if (menus.isNotEmpty()) {
                binding.txtAviso.text = ""
            }else{
                binding.txtAviso.text = "No hay categorías"
            }
        }
    }


    private fun setupRecyclerView() {
        binding.lstMenu.apply {
            this.adapter = MenuAdapter(Menus(), this@MenuActivity)
            layoutManager = LinearLayoutManager(this@MenuActivity)
        }

        binding.btnAddCategory.setOnClickListener {
           model.addCategory(
                token,
                binding.txtAddCategory.editText?.text.toString(),
                id.toString()
            )
        }
        binding.btnAddPlato.setOnClickListener {
            val intent = Intent(this, PlatoActivity::class.java)
            startActivity(intent)
        }
        binding.txtNombreMenu.text = nombre
    }

    override fun onMenuClick(menu: Menu) {
        val listaDialog = ListaDialog(this@MenuActivity)
        if(menu.plates.isNotEmpty()){
            listaDialog.mostrarListaDialog(this, menu.plates)
        }else{
            listaDialog.noHayNada()
        }
    }

    override fun onMenuDelete(menu: Menu) {
        MenuRepository.deleteMenuCategory(token, menu.id!!,
            success = {
                model.fetchMenus(id)
            },
            failure = {
                it.printStackTrace()
            })
    }
}