package com.sro.guessit.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sro.guessit.Service.MusicService
import com.sro.guessit.R
import com.sro.guessit.databinding.ActivityMainBinding
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        lateinit var music: MediaPlayer
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        music = MediaPlayer.create(this, R.raw.gamebackmusic)
        music.isLooping = true



        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        binding.exit.setOnClickListener {
            exitProcess(0)
        }

        binding.btnPlay.setOnClickListener {
            startActivity(Intent(this@MainActivity, GameActivity::class.java))
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

    override fun onStop() {
        super.onStop()
        music.pause()
        Log.d("sdfssf", "mainstop")

    }

    override fun onResume() {
        super.onResume()

        if (getSharedPreferences("settings", MODE_PRIVATE).getBoolean("music", true)) {
            Handler().postDelayed(Runnable {
                music.start()
            }, 800)
        }
        Log.d("sdfssf", "mainresume")

    }

    override fun onRestart() {
        super.onRestart()

        if (getSharedPreferences("settings", MODE_PRIVATE).getBoolean("music", true)) {
            music.start()
            Log.d("sdfssf", "mainrestart")
        }

    }
}
