package com.example.restaurantesapi.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.restaurantesapi.databinding.MenuItemLayoutBinding
import com.example.restaurantesapi.models.Menu
import com.example.restaurantesapi.models.Menus

class MenuAdapter(val menuList: Menus, val listener: OnMenuClickListener) :
    RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding =
            MenuItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return MenuViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val menu = menuList[position]
        holder.bind(menu, listener)

    }

    fun updateData(menuList: Menus) {
        this.menuList.clear()
        this.menuList.addAll(menuList)
        notifyDataSetChanged()
    }

    class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = MenuItemLayoutBinding.bind(itemView)
        fun bind(menu: Menu, listener: OnMenuClickListener) {
            binding.apply {
                txtTipo.text = menu.name
                txtIdMenu.text = menu.id.toString()

                txtTipo.setOnClickListener {
                    listener.onMenuClick(menu)
                }
                btnBorrarMenu.setOnClickListener {
                    listener.onMenuDelete(menu)
                }
                root.setOnClickListener { listener.onMenuClick(menu) }
            }

        }
    }

    interface OnMenuClickListener {
        fun onMenuClick(restaurant: Menu)
        fun onMenuDelete(restaurant: Menu)
    }
}