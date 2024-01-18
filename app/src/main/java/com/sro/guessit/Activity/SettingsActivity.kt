package com.sro.guessit.Activity

import android.content.Context
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSound.setOnClickListener {
            if (getSettings()) {
                MainActivity.music.pause()
                binding.btnSound.setImageResource(R.drawable.mute)
                saveSettings(false)
            } else {
                saveSettings(true)
                MainActivity.music.start()
                binding.btnSound.setImageResource(R.drawable.sound)
            }
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


    public fun saveSettings(isSound: Boolean) {


        val sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        if (!isSound) {
            editor.putBoolean("music", false)
        } else {
            editor.putBoolean("music", true)
        }
        editor.apply()
    }

    public fun getSettings(): Boolean {
        return getSharedPreferences("settings", MODE_PRIVATE).getBoolean("music", true)
    }

    override fun onStop() {
        super.onStop()
        MainActivity.music.pause()
        Log.d("sdfssf", "constop")


    }

    override fun onResume() {
        super.onResume()

        if (getSettings()) {
            Handler().postDelayed(Runnable {
                MainActivity.music.start()
            }, 800)
        }
        Log.d("sdfssf", "conresume")

    }

    override fun onRestart() {
        super.onRestart()

        if (getSettings()) {
            MainActivity.music.start()
            Log.d("sdfssf", "conrestart")
        }
    }
}