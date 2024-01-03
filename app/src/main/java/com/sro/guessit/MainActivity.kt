package com.sro.guessit

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sro.guessit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    companion object {
        lateinit var sound: MediaPlayer
    }

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sound = MediaPlayer.create(this, R.raw.gamebackmusic)
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        sound.start()
        sound.isLooping = true

        binding.exit.setOnClickListener {
            System.exit(0)
        }

        binding.btnPlay.setOnClickListener {
            startActivity(Intent(this@MainActivity, LevelActivity::class.java))
        }

        binding.btnSetting.setOnClickListener {
            startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
        }


        if (getLevelList("levels").isNullOrEmpty()) {
            saveLevelList("levels", listOf(1))
        } else {
        }
    }

    fun saveLevelList(key: String, list: List<Int>) {
        val jsonString = Gson().toJson(list)
        sharedPreferences.edit().putString(key, jsonString).apply()
    }

    fun getLevelList(key: String): List<Int> {
        val jsonString = sharedPreferences.getString(key, "")
        return Gson().fromJson(jsonString, object : TypeToken<List<Int>>() {}.type) ?: emptyList()
    }
}
