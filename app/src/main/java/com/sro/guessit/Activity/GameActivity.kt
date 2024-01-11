package com.sro.guessit.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.sro.guessit.Fragment.GameFragement
import com.sro.guessit.Fragment.LevelFragment
import com.sro.guessit.R
import com.sro.guessit.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {
    lateinit var level: String
    lateinit var binding: ActivityGameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeMobilAds()
        MainActivity.music.start()
        level = intent.getIntExtra("selectedLevel", 0).toString()

//        loadLevelFragment(Integer.parseInt(level))
//        loadLevelFragment(1)
        loadFragment()
    }

    private fun loadFragment() {


        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.gameContainer, LevelFragment())
        fragmentTransaction.commit()


    }

    override fun onResume() {
        super.onResume()

        Handler().postDelayed(Runnable {
            MainActivity.music.start()
            Log.d("sdfssf", "mainresume")

        }, 800)


    }

    private fun initializeMobilAds() {
//        Handler().postDelayed({
//            MobileAds.initialize(this@GameActivity)
//            findViewById<AdView>(R.id.adView)
//                ?.loadAd(AdRequest.Builder().build())
//        }, 600)
    }

    private fun loadLevelFragment(level: Int) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        val gameFragment: GameFragement = GameFragement.newInstance(level)!!
        fragmentTransaction.replace(R.id.gameContainer, gameFragment)
        fragmentTransaction.commit()
    }

    override fun onStop() {
        super.onStop()
        MainActivity.music.pause()
        Log.d("sdfssf", "gameresume")

    }

    override fun onRestart() {
        super.onRestart()
        MainActivity.music.start()
        Log.d("sdfssf", "gameresume")


    }

    override fun onPause() {
        super.onPause()
        Log.d("sdfssf", "gameresume")


    }

}