package com.healthy_eats

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.healthy_eats.databinding.ActivityFavoritosBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ActivityFavoritos : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritosBinding
    private val database = FirebaseDatabase.getInstance()
    private val userId = FirebaseAuth.getInstance().currentUser?.uid
    private lateinit var recetasFavoritasList: ArrayList<Receta>
    private lateinit var adapter: RecetasAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recetasFavoritasList = ArrayList()
        adapter = RecetasAdapter(recetasFavoritasList)

        binding.rvFavoritos.layoutManager = LinearLayoutManager(this)
        binding.rvFavoritos.adapter = adapter

        cargarRecetasFavoritas()
    }

    private fun cargarRecetasFavoritas() {
        userId?.let {
            val ref = database.getReference("favoritos").child(it)
            ref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    recetasFavoritasList.clear()
                    for (dataSnapshot in snapshot.children) {
                        val receta = dataSnapshot.getValue(Receta::class.java)
                        receta?.let { recetasFavoritasList.add(it) }
                    }
                    if (recetasFavoritasList.isEmpty()) {
                        Toast.makeText(this@ActivityFavoritos, "No hay recetas favoritas", Toast.LENGTH_SHORT).show()
                    }
                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@ActivityFavoritos, "Error al cargar recetas favoritas: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
