package com.sro.guessit

import android.icu.number.IntegerWidth
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.sro.guessit.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {
    lateinit var level: String
    lateinit var binding: ActivityGameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        level = intent.getIntExtra("selectedLevel", 0).toString()

        loadLevelFragment(Integer.parseInt(level))

     }

    private fun loadLevelFragment(level: Int) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        val gameFragment: GameFragement = GameFragement.newInstance(level)!!
        fragmentTransaction.replace(R.id.gameContainer, gameFragment)
        fragmentTransaction.commit()
    }


}