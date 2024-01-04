package com.sro.guessit

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.os.IBinder
import android.provider.MediaStore.Audio.Media
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sro.guessit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var musicService: MusicService? = null

    private lateinit var sharedPreferences: SharedPreferences
    companion object {
        public lateinit var mediaPlayer: MediaPlayer
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mediaPlayer = MediaPlayer.create(this, R.raw.gamebackmusic)
        mediaPlayer.isLooping = true

        mediaPlayer.start()

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
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
