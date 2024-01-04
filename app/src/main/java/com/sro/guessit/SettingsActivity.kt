package com.sro.guessit

import android.app.UiModeManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.net.Uri
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
                MainActivity.mediaPlayer.pause()
                binding.btnSound.setImageResource(R.drawable.mute)
            } else {
                MainActivity.mediaPlayer.start()
                binding.btnSound.setImageResource(R.drawable.sound)
            }
            isSound = !isSound
        }

        binding.privacy.setOnClickListener {
            val url = "https://sites.google.com/view/guessitprivacypolicy"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.setPackage("com.android.chrome")
            startActivity(intent)

        }

    }
}