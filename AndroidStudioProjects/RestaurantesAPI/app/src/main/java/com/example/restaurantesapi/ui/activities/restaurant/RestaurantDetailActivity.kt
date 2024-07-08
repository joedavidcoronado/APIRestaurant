package com.example.restaurantesapi.ui.activities.restaurant

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.restaurantesapi.R
import com.example.restaurantesapi.databinding.ActivityRestaurantDetailBinding
import com.example.restaurantesapi.repositories.objetos.PreferencesRepository
import com.example.restaurantesapi.ui.activities.inicio.FullScreenImageActivity
import com.example.restaurantesapi.ui.activities.menu.MenuActivity
import com.example.restaurantesapi.ui.activities.reserva.LlenadoActivity
import com.example.restaurantesapi.ui.adapters.ImageAdapter
import com.example.restaurantesapi.ui.viewmodels.restaurant.RestaurantDetailViewModel
import com.google.android.material.tabs.TabLayoutMediator


class RestaurantDetailActivity : AppCompatActivity() {
    var id: Int = 0
    var token: String = ""
    var name: String = ""

    private lateinit var binding: ActivityRestaurantDetailBinding
    private var fileChooserResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data

                val bitmap = getBitmapFromIntent(data)
                binding.imgLogoo.setImageBitmap(bitmap)

            }
        }

    private fun getBitmapFromIntent(data: Intent?): Bitmap? {
        val imgUrl = data?.data

        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)

        val cursor = contentResolver.query(
            imgUrl!!,
            filePathColumn, null, null, null
        )
        cursor!!.moveToFirst()

        val columnIndex = cursor.getColumnIndex(filePathColumn[0])
        val picturePath = cursor.getString(columnIndex)
        cursor.close()

        val bitmap = BitmapFactory.decodeFile(picturePath)
        return bitmap
    }

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

        id = intent.getIntExtra("restaurantId", -1)
        token = PreferencesRepository.getToken(this@RestaurantDetailActivity).toString()


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
            binding.viewPager2.adapter = adapter
            TabLayoutMediator(binding.storyTabs2, binding.viewPager2) { tab, position -> }.attach()

            binding.apply {
                Glide.with(this@RestaurantDetailActivity)
                    .load(it.logo)
                    .into(imgLogoo)
                txtNewName2.editText?.setText(it.name)
                txtNewAddres.editText?.setText(it.address)
                txtNewCity2.editText?.setText(it.city)
                txtNewDescription2.editText?.setText(it.description)
            }
        }
    }

    private fun setupEventListeners() {
        binding.btnSaveRestaurannt.setOnClickListener {
            model.saveRestaurant(
                token,
                binding.txtNewName2.editText?.text.toString(),
                binding.txtNewAddres.editText?.text.toString(),
                binding.txtNewCity2.editText?.text.toString(),
                binding.txtNewDescription2.editText?.text.toString(),
                id
            )
        }
        binding.fabSubirLogo2.setOnClickListener {
            openFileSelector()
        }
        binding.fabSubirImagen2.setOnClickListener {
            openFileSelector()
        }
        binding.btnMenu2.setOnClickListener {
            name = intent.getStringExtra("nombre").toString()
            val intent = Intent(this, MenuActivity::class.java)
            intent.putExtra("restaurantId2", id)
            intent.putExtra("restaurantName", name)
            startActivity(intent)
        }
        binding.btnReserva2.setOnClickListener {
            val intent = Intent(this, LlenadoActivity::class.java)
            intent.putExtra("restauranId222", id)
            startActivity(intent)
        }
    }


    private fun openFileSelector() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("image/*")
        fileChooserResultLauncher.launch(intent)
    }
}
