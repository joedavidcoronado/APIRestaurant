package com.example.restaurantesapi.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.restaurantesapi.R
import com.example.restaurantesapi.models.Photo


class ImageAdapter(private val images: List<Photo>) :
    RecyclerView.Adapter<ImageAdapter.StoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.img_item, parent, false)
        return StoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.bind(images[position].url)
    }

    class StoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgStory: ImageView = itemView.findViewById(R.id.imgStory)


        fun bind(imagePath: String) {
            Glide.with(itemView)
                .load(imagePath)
                .into(imgStory)
        }
    }

}