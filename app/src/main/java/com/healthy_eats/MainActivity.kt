package com.healthy_eats

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.healthy_eats.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPerfil.setOnClickListener {
            startActivity(Intent(this, ActivityPerfil::class.java))
        }

        binding.btnBuscarRecetas.setOnClickListener {
            startActivity(Intent(this, ActivityBusqueda::class.java))
        }

        binding.btnFavoritos.setOnClickListener {
            startActivity(Intent(this, ActivityFavoritos::class.java))
        }

        binding.btnNotificaciones.setOnClickListener {
            startActivity(Intent(this, ActivityNotificaciones::class.java))
        }
    }
}
