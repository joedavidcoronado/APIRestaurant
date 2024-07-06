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
import com.example.restaurantesapi.ui.activities.menu.MenuActivity
import com.example.restaurantesapi.ui.adapters.ImageAdapter
import com.example.restaurantesapi.ui.viewmodels.RestaurantDetailViewModel
import com.google.android.material.tabs.TabLayoutMediator

class RestaurantDetailActivity : AppCompatActivity() {
    var id : Int = 0
    var token: String = ""

    private var fileChooserResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data

                val bitmap = getBitmapFromIntent(data)
                binding.imgLogo.setImageBitmap(bitmap)

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

        id = intent.getIntExtra("restaurantId", -1)
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
        binding.fabSubirLogo.setOnClickListener {
            /*model.saveLogo(
                openFileSelector()
            )*/
        }
        binding.fabSubirImagen.setOnClickListener {
            /*model.saveLogo(
                openFileSelector()
            )*/
        }
        binding.btnMenu.setOnClickListener{
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
    }

    private fun openFileSelector() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("image/*")
        fileChooserResultLauncher.launch(intent)
    }
}