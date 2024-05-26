package com.healthy_eats

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.healthy_eats.databinding.ActivityPerfilBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

data class UserProfile(val name: String, val email: String, val preferencias: List<String>, val alergias: List<String>)

class ActivityPerfil : AppCompatActivity() {

    private lateinit var binding: ActivityPerfilBinding
    private val database = FirebaseDatabase.getInstance()
    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userId?.let { cargarPerfil(it) }

        binding.btnGuardarPerfil.setOnClickListener {
            guardarPerfil()
        }
    }

    private fun cargarPerfil(userId: String) {
        val ref = database.getReference("profiles").child(userId)
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val perfil = snapshot.getValue(UserProfile::class.java)
                perfil?.let {
                    binding.etName.setText(it.name)
                    binding.etEmail.setText(it.email)
                    binding.etPreferencias.setText(it.preferencias.joinToString(", "))
                    binding.etAlergias.setText(it.alergias.joinToString(", "))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Manejar error
            }
        })
    }

    private fun guardarPerfil() {
        val name = binding.etName.text.toString()
        val email = binding.etEmail.text.toString()
        val preferencias = binding.etPreferencias.text.toString().split(",").map { it.trim() }
        val alergias = binding.etAlergias.text.toString().split(",").map { it.trim() }

        if (userId != null) {
            val perfil = UserProfile(name, email, preferencias, alergias)
            database.getReference("profiles").child(userId).setValue(perfil).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Perfil guardado exitosamente", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Error al guardar el perfil", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
