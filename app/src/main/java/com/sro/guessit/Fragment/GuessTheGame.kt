package com.sro.guessit.Fragment

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Matrix
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sro.guessit.Activity.MainActivity
import com.sro.guessit.Constants
import com.sro.guessit.Dialog.GameDialog
import com.sro.guessit.Dialog.HintDialog
import com.sro.guessit.R
import com.sro.guessit.databinding.FragmentGuessTheGameBinding
import java.util.regex.Matcher
import java.util.regex.Pattern

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class GuessTheGame : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentGuessTheGameBinding
    private lateinit var gameList: List<String>
    private lateinit var answersList: List<String>
    private lateinit var imageList: List<Int>
    private lateinit var selectedItem: String
    private var currentLevel: Int = 0
    private lateinit var music: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGuessTheGameBinding.inflate(inflater, container, false)
        val constants = Constants()

        binding.hintbal.text = "Hint: ${getHintBalance()}"



        gameList = constants.gameList
        answersList = mutableListOf(
            "Call of Duty 4: Modern Warfare",
            "Freedom Fighters",
            "Grand Theft Auto V",
            "Super Mario Bros",
            "PUBG Mobile",
            "Battlefield 4",
            "Fall Guys",
            "Far Cry 3",
            "Bounce",
            "Subway Surfers",
            "Among Us",
            "Tomb Raider",
            "Temple Run",
            "Minecraft",
            "IGI 2 Covert Strike",
            "Cricket 07",
            "Fallout: New Vegas",
            "Hill Climb Racing",
            "Plants vs Zombies",
            "8 Ball Pool",
            "Fifa",
            "Fortnite",
            "Candy Crush Saga",
            "CYBERPUNK 2077",
            "Valorant",
            "Dota 2",
            "Apex Legends",
            "Hitman",
            "Resident Evil 4",
            "It Takes Two",
            "Scarlet Nexus",
            "Clash Royale",
            "Roblox",
            "Alto's Odyssey",
            "Clash of Clans",
            "Idle Miner Tycoon",
            "Red Ball 4",
            "GTA San Andreas",
            "Tekken",
            "GTA Vice City",
            "The Sims",
            "Wolfenstein The New Order",
            "Microsoft Flight Simulator",
            "Red Dead Redemption",
            "The Last of Us",
            "God of war",
            "The Witcher",
            "Mass Effect 1",
            "Metal Gear Solid 2: Sons of liberty",
            "Grand Theft Auto IV",
            "Half Life 2",
            "Alan Wake 2",
            "Elden Ring",
            "Uncharted 2: Among Thieves"
        )

        imageList = mutableListOf(
            R.drawable.codmodernwarefare,
            R.drawable.freedomfighters,
            R.drawable.gtafive,
            R.drawable.mario,
            R.drawable.pubgmobile,
            R.drawable.battelefieldfour,
            R.drawable.fallguys,
            R.drawable.farcrythree,
            R.drawable.bounce,
            R.drawable.subwaysurfer,
            R.drawable.amongus,
            R.drawable.tombraider,
            R.drawable.templerun,
            R.drawable.minecraft,
            R.drawable.igitwo,
            R.drawable.cricketoseven,
            R.drawable.falloutnewvegas,
            R.drawable.hillclimracing,
            R.drawable.pvz,
            R.drawable.eightballpool,
            R.drawable.fifa,
            R.drawable.fortnite,
            R.drawable.candycrushsagaaga,
            R.drawable.cyberpunk,
            R.drawable.valorant,
            R.drawable.dotatwo,
            R.drawable.apex,
            R.drawable.hitman,
            R.drawable.resident,
            R.drawable.ittakestwo,
            R.drawable.scarlet,
            R.drawable.clashroyal,
            R.drawable.roblox,
            R.drawable.alto,
            R.drawable.coc,
            R.drawable.minertycoon,
            R.drawable.redball,
            R.drawable.gtasan,
            R.drawable.tekken,
            R.drawable.gtavicecity,
            R.drawable.thesims,
            R.drawable.wolf,
            R.drawable.microsoftflight,
            R.drawable.reddead,
            R.drawable.thlastofus,
            R.drawable.thegodofwar,
            R.drawable.thewitcher,
            R.drawable.masseffect,
            R.drawable.metalgear,
            R.drawable.gtafour,
            R.drawable.halflifetwo,
            R.drawable.alanwake,
            R.drawable.eldenring,
            R.drawable.unchartedtwo
        )
        music = MediaPlayer.create(requireContext(), R.raw.gamebackmusic)
        music.isLooping = true
        music.start()

        currentLevel = activity?.getSharedPreferences(
            "levelsfile",
            Context.MODE_PRIVATE
        )?.getInt("levels", 0)!!

        if (currentLevel < 54) {
            binding.gameImage.setImageResource(imageList[currentLevel])
            binding.levelnumber.setText("Level $currentLevel/53")
        } else {
            binding.gameImage.setImageResource(imageList[currentLevel])
            binding.levelnumber.setText("Level $currentLevel/53")


        }
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, gameList)

        // Set the adapter to the AutoCompleteTextView
        binding.autoCompleteTextView.setAdapter(adapter)

        // Optional: Set an item click listener for handling the selected item

        binding.autoCompleteTextView.setOnItemClickListener { _, _, position, _ ->
            selectedItem = adapter.getItem(position).toString()
            val inputMethodManager =
                binding.autoCompleteTextView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(binding.autoCompleteTextView.windowToken, 0)
        }

        binding.ok.setOnClickListener {

            if (selectedItem.equals(
                    answersList[activity?.getSharedPreferences(
                        "levelsfile",
                        Context.MODE_PRIVATE
                    )?.getInt("levels", 0)!!], ignoreCase = true
                )
            ) {
                saveLevel()
                var hintbal = getHintBalance()
                hintbal = hintbal!! + 2
                saveHint(hintbal)
                binding.hintbal.text ="Hint: $hintbal"
                    if (currentLevel < 54) {
                        GameDialog.show(context, 3, object : GameDialog.CustomDialogInterface {
                            override fun onPositiveButtonClick() {
                                binding.gameImage.setImageResource(imageList[currentLevel])
                                binding.levelnumber.text = "Level $currentLevel/53"
                                binding.autoCompleteTextView.clearListSelection()
                            }
                        }, "Level Completed")
                    } else {
                        saveLevel()

                        GameDialog.show(context, 3, object : GameDialog.CustomDialogInterface {
                            override fun onPositiveButtonClick() {
                                startActivity(Intent(activity, MainActivity::class.java))
                            }


                        }, "Congratulations!! You completed all the levels")
                    }


            } else {
                Toast.makeText(requireContext(), "Incorrect", Toast.LENGTH_LONG).show()
            }
        }

        binding.hint.setOnClickListener {
            hintSystem()
        }

        return binding.root
    }

    private fun getHintBalance(): Int? {
        val sharedPreferences =
            context?.getSharedPreferences("hint", Context.MODE_PRIVATE)

        return sharedPreferences?.getInt("hintBalanceKey", 10)

    }

    private fun hintSystem() {


        val hinttext = view?.findViewById<TextView>(R.id.hintbal)
        var hintBalance = extractDigits(hinttext?.text.toString())

        if (hintBalance > 0) {

            HintDialog.show(context, object : HintDialog.HintDialogInterface {
                override fun onYesClickListener(dialog: Dialog?) {
                    hintBalance--
                    saveHint(hintBalance)
                    hinttext?.text = "Hint: $hintBalance"
                    var answer = answersList[currentLevel]
                    Toast.makeText(requireContext(), "Answer is: $answer", Toast.LENGTH_LONG).show()
                }

                override fun onNoClickListener(dialog: Dialog?) {

                }


            }, "Wanna use hint?")


        } else {
            Toast.makeText(context, "Watch video to earn hint", Toast.LENGTH_LONG).show()
        }

    }


    private fun saveHint(hintBalance: Int) {
        val sharedPreferences =
            context?.getSharedPreferences("hint", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()

        editor?.putInt("hintBalanceKey", hintBalance)

        editor?.apply()
    }


    private fun extractDigits(input: String): Int {
        val pattern: Pattern = Pattern.compile("\\d+")

        val matcher: Matcher = pattern.matcher(input)

        return if (matcher.find()) {
            val digits = matcher.group()
            digits.toInt()
        } else {
            println("No digits found in the input string.")
            -1
        }
    }

    override fun onStop() {
        super.onStop()
        music.pause()
    }

    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            val scaleFactor = detector.scaleFactor
            Matrix().postScale(scaleFactor, scaleFactor, detector.focusX, detector.focusY)
            binding.gameImage.imageMatrix = Matrix()
            return true
        }
    }


    private fun saveLevel() {

        if (currentLevel <= 52) {
            currentLevel += 1
            activity?.getSharedPreferences(
                "levelsfile",
                Context.MODE_PRIVATE
            )?.edit()?.putInt("levels", currentLevel)?.apply()

        } else {

            activity?.getSharedPreferences(
                "levelsfile",
                Context.MODE_PRIVATE
            )?.edit()?.putInt("levels", 0)?.apply()


        }
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GuessTheGame().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}