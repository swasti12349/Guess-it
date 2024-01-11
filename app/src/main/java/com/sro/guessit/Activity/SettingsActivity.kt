package com.sro.guessit.Activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.sro.guessit.R
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
                MainActivity.music.pause()
                binding.btnSound.setImageResource(R.drawable.mute)
            } else {
                MainActivity.music.start()
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


        binding.contact.setOnClickListener {
            startActivity(Intent(this@SettingsActivity, ContactActivity::class.java))
        }
    }


    override fun onStop() {
        super.onStop()
        MainActivity.music.pause()
        Log.d("sdfssf", "constop")


    }

    override fun onResume() {
        super.onResume()
        Handler().postDelayed(Runnable {
            MainActivity.music.start()
        }, 800)

        Log.d("sdfssf", "conresume")

    }

    override fun onRestart() {
        super.onRestart()
        MainActivity.music.start()
        Log.d("sdfssf", "conrestart")
    }
}