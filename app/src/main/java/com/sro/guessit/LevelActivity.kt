package com.sro.guessit

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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

        if (levelList.size % 3 == 0) {
            HintDialog.show(this@LevelActivity, object : HintDialog.HintDialogInterface {
                override fun onYesClickListener(dialog: android.app.Dialog?) {
                    openPlayStore(this@LevelActivity)
                }

                override fun onNoClickListener(dialog: android.app.Dialog?) {
                }
            }, "Do you like it?")
        }
    }

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

    override fun onResume() {
        super.onResume()
        // Refresh levelList when the activity is resumed
        levelList = getLevelList("levels")
        val adapter = GridAdapter(this, levels, levelList)
        binding.levelsGV.adapter = adapter
    }

    private fun getLevelList(key: String): List<Int> {
        val jsonString = sharedPreferences.getString(key, "")
        return Gson().fromJson(jsonString, object : TypeToken<List<Int>>() {}.type) ?: emptyList()
    }
}
