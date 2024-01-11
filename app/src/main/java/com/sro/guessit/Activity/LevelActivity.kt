package com.sro.guessit.Activity

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sro.guessit.Adapter.GridAdapter
import com.sro.guessit.Dialog.HintDialog
import com.sro.guessit.databinding.ActivityLevelBinding

class LevelActivity : AppCompatActivity() {
    lateinit var levels: ArrayList<Int>
    lateinit var binding: ActivityLevelBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var levelList: List<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        levels = ArrayList()
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        for (i in 1..50) {
            levels.add(i)
        }

        levelList = getLevelList("levels")

        binding = ActivityLevelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = GridAdapter(this, levels, levelList)
        binding.levelsGV.adapter = adapter

        binding.levelsGV.setOnItemClickListener { parent, view, position, id ->
            val selectedLevel = levels[position]
            val intent = Intent(this@LevelActivity, GameActivity::class.java)
            intent.putExtra("selectedLevel", selectedLevel)
            startActivity(intent)
        }

        if (levelList.size % 5 == 0) {
            HintDialog.show(this@LevelActivity, object : HintDialog.HintDialogInterface {
                override fun onYesClickListener(dialog: Dialog?) {
                    openPlayStore(this@LevelActivity)
                }

                override fun onNoClickListener(dialog: Dialog?) {
                }
            }, "Do you like it? Rate us")
        }
    }

    companion object {
        fun openPlayStore(context: Context) {
            val appPackageName = context.packageName
            try {
                context.startActivity(
                    Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName"))
                )
            } catch (e: android.content.ActivityNotFoundException) {
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                    )
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()


        Handler().postDelayed(Runnable {
            levelList = getLevelList("levels")
            val adapter = GridAdapter(this, levels, levelList)
            binding.levelsGV.adapter = adapter
            MainActivity.music.start()
            Log.d("sdfssf", "levres")
        }, 800)

    }

    override fun onStop() {
        super.onStop()
        MainActivity.music.pause()
        Log.d("sdfssf", "levstop")

    }

    override fun onRestart() {
        super.onRestart()


        MainActivity.music.start()
        Log.d("sdfssf", "levrestart")


    }

    private fun getLevelList(key: String): List<Int> {
        val jsonString = sharedPreferences.getString(key, "")
        return Gson().fromJson(jsonString, object : TypeToken<List<Int>>() {}.type) ?: emptyList()
    }


}

