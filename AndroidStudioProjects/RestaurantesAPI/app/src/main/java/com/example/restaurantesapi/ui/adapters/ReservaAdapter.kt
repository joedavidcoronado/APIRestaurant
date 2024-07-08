package com.example.restaurantesapi.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantesapi.databinding.ReservaItemLayoutBinding
import com.example.restaurantesapi.models.Reserva
import com.example.restaurantesapi.models.Reservas


class ReservaAdapter(val reservaList: Reservas, val listener: OnReservaClickListener) :
    RecyclerView.Adapter<ReservaAdapter.ReservaViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservaViewHolder {
        val binding =
            ReservaItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ReservaViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return reservaList.size
    }

    override fun onBindViewHolder(holder: ReservaViewHolder, position: Int) {
        val reserva = reservaList[position]
        holder.bind(reserva, listener)
    }

    fun updateData(reservaList: Reservas) {
        this.reservaList.clear()
        this.reservaList.addAll(reservaList)
        notifyDataSetChanged()
    }

    class ReservaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ReservaItemLayoutBinding.bind(itemView)
        fun bind(reserva: Reserva, listener: OnReservaClickListener) {
            binding.apply {
                txtNombreRes.text = reserva.restaurant?.name
                txtHoraRes.text = reserva.time
                txtFechaRes.text = reserva.date
                txtPersonas.text = reserva.people.toString()
                txtEstatus.text = reserva.status


                /*txtTipo.setOnClickListener {
                    listener.onReservaClick(reserva)
                }
                btnBorrarReserva.setOnClickListener {
                    listener.onReservaDelete(reserva)
                }*/
                root.setOnClickListener { listener.onReservaClick(reserva) }
            }

        }
    }

    interface OnReservaClickListener {
        fun onReservaClick(restaurant: Reserva)
        fun onReservaDelete(restaurant: Reserva)
    }
}