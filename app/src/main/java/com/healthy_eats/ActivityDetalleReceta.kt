package com.healthy_eats

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.healthy_eats.databinding.ActivityDetalleRecetaBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ActivityDetalleReceta : AppCompatActivity() {

    private lateinit var binding: ActivityDetalleRecetaBinding
    private val database = FirebaseDatabase.getInstance()
    private val userId = FirebaseAuth.getInstance().currentUser?.uid
    private lateinit var receta: Receta

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleRecetaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recetaId = intent.getStringExtra("RECETA_ID")
        recetaId?.let { cargarDetalleReceta(it) }

        binding.btnGuardarFavoritos.setOnClickListener {
            guardarEnFavoritos()
        }
    }

    private fun cargarDetalleReceta(recetaId: String) {
        val ref = database.getReference("recipes").child(recetaId)
        ref.get().addOnSuccessListener { snapshot ->
            receta = snapshot.getValue(Receta::class.java) ?: Receta()
            binding.tvNombreReceta.text = receta.nombre
            binding.tvIngredientes.text = receta.ingredientes.joinToString("\n")
            binding.tvInstrucciones.text = receta.instrucciones
            binding.tvValorNutricional.text = receta.valorNutricional
        }.addOnFailureListener {
            Toast.makeText(this, "Error al cargar la receta", Toast.LENGTH_SHORT).show()
        }
    }

    private fun guardarEnFavoritos() {
        userId?.let {
            val ref = database.getReference("favoritos").child(it).child(receta.nombre)
            ref.setValue(receta).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Receta guardada en favoritos", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Error al guardar en favoritos", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
