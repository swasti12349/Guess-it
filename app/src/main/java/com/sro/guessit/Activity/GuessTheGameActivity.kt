package com.sro.guessit.Activity

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sro.guessit.Fragment.GuessTheGame
import com.sro.guessit.Fragment.LevelFragment
import com.sro.guessit.R

class GuessTheGameActivity : AppCompatActivity() {
     lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guess_the_game)

        loadFragment()
        sharedPreferences = getSharedPreferences("levels", Context.MODE_PRIVATE)

    }

    private fun loadFragment() {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.gameContainergtg, GuessTheGame())
        fragmentTransaction.commit()
    }
    private fun getLevelList(key: String): List<Int> {
        val jsonString = sharedPreferences.getString(key, "")
        return Gson().fromJson(jsonString, object : TypeToken<List<Int>>() {}.type) ?: emptyList()
    }

}