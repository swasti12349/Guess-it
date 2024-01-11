package com.sro.guessit.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.sro.guessit.Activity.LevelActivity
import com.sro.guessit.databinding.ActivityContactBinding

class ContactActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContactBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rateus.setOnClickListener {
            LevelActivity.openPlayStore(this@ContactActivity)
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