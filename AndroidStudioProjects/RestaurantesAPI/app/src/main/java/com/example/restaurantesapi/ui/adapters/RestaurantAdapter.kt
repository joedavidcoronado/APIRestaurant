package com.example.restaurantesapi.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.restaurantesapi.databinding.RestaurantItemLayoutBinding
import com.example.restaurantesapi.models.Restaurant
import com.example.restaurantesapi.models.Restaurants


class RestaurantAdapter(val restaurantList: Restaurants, val listener: OnRestaurantClickListener) :
    RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val binding =
            RestaurantItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return RestaurantViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return restaurantList.size
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val restaurant = restaurantList[position]
        holder.bind(restaurant, listener)
    }

    fun updateData(restaurantList: Restaurants) {
        this.restaurantList.clear()
        this.restaurantList.addAll(restaurantList)
        notifyDataSetChanged()
    }

    class RestaurantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(restaurant: Restaurant, listener: OnRestaurantClickListener) {
            val binding = RestaurantItemLayoutBinding.bind(itemView)
            binding.apply {
                Glide.with(itemView.context)
                    .load(restaurant.logo.toString())
                    .into(imgImagen)
                txtName.text = restaurant.name

                txtName.setOnClickListener {
                    listener.onRestaurantClick(restaurant)
                }
                root.setOnClickListener { listener.onRestaurantClick(restaurant) }
            }

        }
    }

    interface OnRestaurantClickListener {
        fun onRestaurantClick(restaurant: Restaurant)
        fun onRestaurantDelete(restaurant: Restaurant)
    }
}