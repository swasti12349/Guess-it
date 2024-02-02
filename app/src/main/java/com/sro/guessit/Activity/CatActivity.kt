package com.sro.guessit.Activity

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sro.guessit.Adapter.CatAdapter
import com.sro.guessit.Model.listModel
import com.sro.guessit.R
import com.sro.guessit.databinding.ActivityCatBinding

class CatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCatBinding
    private lateinit var catlist: List<String>
    private lateinit var music: MediaPlayer
    private lateinit var listItem: List<listModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        music = MediaPlayer.create(this, R.raw.gamebackmusic)
        music.isLooping = true
        music.start()
        listItem = mutableListOf(listModel(R.drawable.abc, "Word"), listModel(R.drawable.gamerem, "Game"))

        binding = ActivityCatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        catlist = mutableListOf("Guess the Word", "Guess the Game")


        val adapter = CatAdapter(this@CatActivity,
            listItem as MutableList<listModel>)
        binding.catlistView.adapter = adapter

    }

    override fun onStop() {
        super.onStop()
        music.pause()
    }

    override fun onStart() {
        super.onStart()
        music.start()
    }
}