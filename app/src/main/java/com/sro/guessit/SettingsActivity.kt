package com.sro.guessit

import android.app.UiModeManager
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.sro.guessit.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    lateinit var binding: ActivitySettingsBinding
    private var isSound = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.btnSound.setOnClickListener {
            if (isSound) {
                MainActivity.sound.pause()
                binding.btnSound.setImageResource(R.drawable.mute)
            } else {
                MainActivity.sound.start()
                binding.btnSound.setImageResource(R.drawable.sound)
            }
            isSound = !isSound
        }

    }
}
