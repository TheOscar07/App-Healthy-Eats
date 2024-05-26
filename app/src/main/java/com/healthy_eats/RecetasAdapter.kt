package com.healthy_eats

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecetasAdapter(private val recetasList: ArrayList<Receta>) : RecyclerView.Adapter<RecetasAdapter.RecetaViewHolder>() {

    class RecetaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombre: TextView = itemView.findViewById(R.id.tvNombreReceta)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecetaViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_receta, parent, false)
        return RecetaViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecetaViewHolder, position: Int) {
        val receta = recetasList[position]
        holder.nombre.text = receta.nombre

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, ActivityDetalleReceta::class.java)
            intent.putExtra("RECETA_ID", receta.nombre)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return recetasList.size
    }
}
