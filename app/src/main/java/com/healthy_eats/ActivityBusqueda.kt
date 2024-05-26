package com.healthy_eats

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.healthy_eats.databinding.ActivityBusquedaBinding
import com.google.firebase.database.*

data class Receta(val nombre: String = "", val ingredientes: List<String> = listOf(), val instrucciones: String = "", val valorNutricional: String = "")

class ActivityBusqueda : AppCompatActivity() {

    private lateinit var binding: ActivityBusquedaBinding
    private val database = FirebaseDatabase.getInstance()
    private lateinit var recetasList: ArrayList<Receta>
    private lateinit var adapter: RecetasAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBusquedaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recetasList = ArrayList()
        adapter = RecetasAdapter(recetasList)

        binding.rvRecetas.layoutManager = LinearLayoutManager(this)
        binding.rvRecetas.adapter = adapter

        binding.btnBuscar.setOnClickListener {
            val query = binding.etBuscar.text.toString()
            if (query.isNotEmpty()) {
                buscarRecetas(query)
            } else {
                Toast.makeText(this, "Ingrese un término de búsqueda", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun buscarRecetas(query: String) {
        val ref = database.getReference("recipes")
        ref.orderByChild("nombre").startAt(query).endAt(query + "\uf8ff").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                recetasList.clear()
                for (dataSnapshot in snapshot.children) {
                    val receta = dataSnapshot.getValue(Receta::class.java)
                    receta?.let { recetasList.add(it) }
                }
                if (recetasList.isEmpty()) {
                    Toast.makeText(this@ActivityBusqueda, "No se encontraron recetas", Toast.LENGTH_SHORT).show()
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ActivityBusqueda, "Error al buscar recetas: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
