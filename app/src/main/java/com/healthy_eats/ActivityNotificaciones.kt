package com.healthy_eats

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.healthy_eats.databinding.ActivityNotificacionesBinding

class ActivityNotificaciones : AppCompatActivity() {

    private lateinit var binding: ActivityNotificacionesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificacionesBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
