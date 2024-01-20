package com.sro.guessit.Fragment

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sro.guessit.Dialog.HintDialog
import com.sro.guessit.R
import kotlinx.coroutines.Job
import java.util.Random
import java.util.regex.Matcher
import java.util.regex.Pattern

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class GameFragement : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private lateinit var stopwatchJob: Job
    private var elapsedTime: Long = 0
    private var startTime: Long = 0
    private lateinit var list: MutableList<EditText>
    private lateinit var handler: Handler
    private var isRunning = false
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
        val bundle = arguments
        if (bundle != null) {
            currentLevel = bundle.getInt("selectedLevel", 1)


            // Now, 'receivedData' contains the data sent from FragmentA
        }
        val view: View
        handler = Handler()
        startStopwatch()
        val wordMap = mapOf(
            1 to Triple("CAKE", 2, listOf(R.id.ed1, R.id.ed2, R.id.ed3, R.id.ed4)),
            2 to Triple("BRUSH", 3, listOf(R.id.ed1, R.id.ed2, R.id.ed3, R.id.ed4, R.id.ed5)),
            3 to Triple("MOON", 4, listOf(R.id.ed1, R.id.ed2, R.id.ed3, R.id.ed4)),
            4 to Triple(
                "GOOGLE",
                5,
                listOf(R.id.ed1, R.id.ed2, R.id.ed3, R.id.ed4, R.id.ed5, R.id.ed6)
            ),
            5 to Triple("ECHO", 6, listOf(R.id.ed1, R.id.ed2, R.id.ed3, R.id.ed4)),
            6 to Triple("FIRE", 7, listOf(R.id.ed1, R.id.ed2, R.id.ed3, R.id.ed4)),
            7 to Triple("MAP", 8, listOf(R.id.ed1, R.id.ed2, R.id.ed3)),
            8 to Triple(
                "KEYBOARD",
                9,
                listOf(
                    R.id.ed1,
                    R.id.ed2,
                    R.id.ed3,
                    R.id.ed4,
                    R.id.ed5,
                    R.id.ed6,
                    R.id.ed7,
                    R.id.ed8
                )
            ),
            9 to Triple(
                "SQUASH",
                10,
                listOf(R.id.ed1, R.id.ed2, R.id.ed3, R.id.ed4, R.id.ed5, R.id.ed6)
            ),
            10 to Triple("CLOUD", 11, listOf(R.id.ed1, R.id.ed2, R.id.ed3, R.id.ed4, R.id.ed5)),
            11 to Triple("PIANO", 12, listOf(R.id.ed1, R.id.ed2, R.id.ed3, R.id.ed4, R.id.ed5)),
            12 to Triple(
                "COFFIN",
                13,
                listOf(R.id.ed1, R.id.ed2, R.id.ed3, R.id.ed4, R.id.ed5, R.id.ed6)
            ),
            13 to Triple(
                "CANDLE",
                14,
                listOf(R.id.ed1, R.id.ed2, R.id.ed3, R.id.ed4, R.id.ed5, R.id.ed6)
            ),
            14 to Triple(
                "PROMISE",
                15,
                listOf(R.id.ed1, R.id.ed2, R.id.ed3, R.id.ed4, R.id.ed5, R.id.ed6, R.id.ed7)
            ),
            15 to Triple(
                "CHEESE",
                16,
                listOf(R.id.ed1, R.id.ed2, R.id.ed3, R.id.ed4, R.id.ed5, R.id.ed6)
            ),
            16 to Triple("NAIL", 17, listOf(R.id.ed1, R.id.ed2, R.id.ed3, R.id.ed4)),
            17 to Triple(
                "POCKET",
                18,
                listOf(R.id.ed1, R.id.ed2, R.id.ed3, R.id.ed4, R.id.ed5, R.id.ed6)
            ),
            18 to Triple("QUEUE", 19, listOf(R.id.ed1, R.id.ed2, R.id.ed3, R.id.ed4, R.id.ed5)),
            19 to Triple("WRONG", 20, listOf(R.id.ed1, R.id.ed2, R.id.ed3, R.id.ed4, R.id.ed5)),
            20 to Triple(
                "LOUNGER",
                21,
                listOf(R.id.ed1, R.id.ed2, R.id.ed3, R.id.ed4, R.id.ed5, R.id.ed6, R.id.ed7)
            ),
            21 to Triple("GRAPE", 22, listOf(R.id.ed1, R.id.ed2, R.id.ed3, R.id.ed4, R.id.ed5)),
            22 to Triple(
                "NOTHING",
                23,
                listOf(R.id.ed1, R.id.ed2, R.id.ed3, R.id.ed4, R.id.ed5, R.id.ed6, R.id.ed7)
            ),
            23 to Triple("BANK", 24, listOf(R.id.ed1, R.id.ed2, R.id.ed3, R.id.ed4)),
            24 to Triple("SWIMS", 25, listOf(R.id.ed1, R.id.ed2, R.id.ed3, R.id.ed4, R.id.ed5)),
            25 to Triple("BOOK", 26, listOf(R.id.ed1, R.id.ed2, R.id.ed3, R.id.ed4)),
            26 to Triple(
                "SHADOW",
                27,
                listOf(R.id.ed1, R.id.ed2, R.id.ed3, R.id.ed4, R.id.ed5, R.id.ed6)
            ),
            27 to Triple(
                "BREATH",
                28,
                listOf(R.id.ed1, R.id.ed2, R.id.ed3, R.id.ed4, R.id.ed5, R.id.ed6)
            ),
            28 to Triple("SHIRT", 29, listOf(R.id.ed1, R.id.ed2, R.id.ed3, R.id.ed4, R.id.ed5)),
            29 to Triple("DARK", 30, listOf(R.id.ed1, R.id.ed2, R.id.ed3, R.id.ed4)),
            30 to Triple(
                "MOSQUITO",
                31,
                listOf(
                    R.id.ed1,
                    R.id.ed2,
                    R.id.ed3,
                    R.id.ed4,
                    R.id.ed5,
                    R.id.ed6,
                    R.id.ed7,
                    R.id.ed8
                )
            ),
            31 to Triple(
                "DOCTOR",
                32,
                listOf(R.id.ed1, R.id.ed2, R.id.ed3, R.id.ed4, R.id.ed5, R.id.ed6)
            ),
            32 to Triple(
                "MIRROR",
                33,
                listOf(R.id.ed1, R.id.ed2, R.id.ed3, R.id.ed4, R.id.ed5, R.id.ed6)
            ),
            33 to Triple("AGE", 34, listOf(R.id.ed1, R.id.ed2, R.id.ed3)),
            34 to Triple(
                "DUSTBIN",
                35,
                listOf(R.id.ed1, R.id.ed2, R.id.ed3, R.id.ed4, R.id.ed5, R.id.ed6, R.id.ed7)
            ),
            35 to Triple("HOLE", 36, listOf(R.id.ed1, R.id.ed2, R.id.ed3, R.id.ed4)),
            36 to Triple("KEY", 37, listOf(R.id.ed1, R.id.ed2, R.id.ed3)),
            37 to Triple("CLOCK", 38, listOf(R.id.ed1, R.id.ed2, R.id.ed3, R.id.ed4, R.id.ed5)),
            38 to Triple(
                "SPONGE",
                39,
                listOf(R.id.ed1, R.id.ed2, R.id.ed3, R.id.ed4, R.id.ed5, R.id.ed6)
            ),
            39 to Triple(
                "FLORIDA",
                40,
                listOf(R.id.ed1, R.id.ed2, R.id.ed3, R.id.ed4, R.id.ed5, R.id.ed6, R.id.ed7)
            ),
            40 to Triple("TOWEL", 41, listOf(R.id.ed1, R.id.ed2, R.id.ed3, R.id.ed4)),
            41 to Triple("RIVER", 42, listOf(R.id.ed1, R.id.ed2, R.id.ed3, R.id.ed4, R.id.ed5)),
            42 to Triple(
                "NORWAY",
                43,
                listOf(R.id.ed1, R.id.ed2, R.id.ed3, R.id.ed4, R.id.ed5, R.id.ed6)
            ),
            43 to Triple("OCEAN", 44, listOf(R.id.ed1, R.id.ed2, R.id.ed3, R.id.ed4, R.id.ed5)),
            44 to Triple("STAMPS", 45, listOf(R.id.ed1, R.id.ed2, R.id.ed3, R.id.ed4, R.id.ed5)),
            45 to Triple(
                "HONEYCOMB",
                46,
                listOf(
                    R.id.ed1,
                    R.id.ed2,
                    R.id.ed3,
                    R.id.ed4,
                    R.id.ed5,
                    R.id.ed6,
                    R.id.ed7,
                    R.id.ed8,
                    R.id.ed9
                )
            ),
            46 to Triple(
                "LETTUCE",
                47,
                listOf(R.id.ed1, R.id.ed2, R.id.ed3, R.id.ed4, R.id.ed5, R.id.ed6, R.id.ed7)
            ),
            47 to Triple("JEEP", 48, listOf(R.id.ed1, R.id.ed2, R.id.ed3, R.id.ed4)),
            48 to Triple(
                "BALLOON",
                49,
                listOf(R.id.ed1, R.id.ed2, R.id.ed3, R.id.ed4, R.id.ed5, R.id.ed6)
            ),
            49 to Triple("TREE", 50, listOf(R.id.ed1, R.id.ed2, R.id.ed3, R.id.ed4)),
            50 to Triple("INDIA", 51, listOf(R.id.ed1, R.id.ed2, R.id.ed3, R.id.ed4, R.id.ed5))
        )

        val (word, nextLevel, editTextIds) = wordMap[currentLevel]
            ?: throw IllegalArgumentException("Invalid level: $currentLevel")

        view = initializeLevelView(inflater, container, currentLevel, word, editTextIds)
        return view
    }


    fun initializeLevelView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        level: Int,
        word: String,
        editTextIds: List<Int>
    ): View {
        val view = inflater.inflate(getLayoutResourceId(level), container, false)
        val list = editTextIds.map { view.findViewById<EditText>(it) }

        initializeMobilAds(view)

        view.findViewById<ImageView>(R.id.hint).setOnClickListener {
            hintSystem(word)
        }

        initializeLevel(word, list, level + 1, view)

        return view
    }


    fun getLayoutResourceId(level: Int): Int {
        return when (level) {
            1 -> R.layout.fragment_game_level1
            2 -> R.layout.fragment_game_level2
            3 -> R.layout.fragment_game_level3
            4 -> R.layout.fragment_game_level4
            5 -> R.layout.fragment_game_level5
            6 -> R.layout.fragment_game_level6
            7 -> R.layout.fragment_game_level7
            8 -> R.layout.fragment_game_level8
            9 -> R.layout.fragment_game_level9
            10 -> R.layout.fragment_game_level10
            11 -> R.layout.fragment_game_level11
            12 -> R.layout.fragment_game_level12
            13 -> R.layout.fragment_game_level13
            14 -> R.layout.fragment_game_level14
            15 -> R.layout.fragment_game_level15
            16 -> R.layout.fragment_game_level16
            17 -> R.layout.fragment_game_level17
            18 -> R.layout.fragment_game_level18
            19 -> R.layout.fragment_game_level19
            20 -> R.layout.fragment_game_level20
            21 -> R.layout.fragment_game_level21
            22 -> R.layout.fragment_game_level22
            23 -> R.layout.fragment_game_level23
            24 -> R.layout.fragment_game_level24
            25 -> R.layout.fragment_game_level25
            26 -> R.layout.fragment_game_level26
            27 -> R.layout.fragment_game_level27
            28 -> R.layout.fragment_game_level28
            29 -> R.layout.fragment_game_level29
            30 -> R.layout.fragment_game_level30
            31 -> R.layout.fragment_game_level31
            32 -> R.layout.fragment_game_level32
            33 -> R.layout.fragment_game_level33
            34 -> R.layout.fragment_game_level34
            35 -> R.layout.fragment_game_level35
            36 -> R.layout.fragment_game_level36
            37 -> R.layout.fragment_game_level37
            38 -> R.layout.fragment_game_level38
            39 -> R.layout.fragment_game_level39
            40 -> R.layout.fragment_game_level40
            41 -> R.layout.fragment_game_level41
            42 -> R.layout.fragment_game_level42
            43 -> R.layout.fragment_game_level43
            44 -> R.layout.fragment_game_level44
            45 -> R.layout.fragment_game_level45
            46 -> R.layout.fragment_game_level46
            47 -> R.layout.fragment_game_level47
            48 -> R.layout.fragment_game_level48
            49 -> R.layout.fragment_game_level49
            50 -> R.layout.fragment_game_level50

            else -> throw IllegalArgumentException("Invalid level: $level")
        }
    }

    private fun initializeMobilAds(view: View?) {
//        Handler().postDelayed({
//            MobileAds.initialize(requireContext())
//            view?.findViewById<AdView>(R.id.adView)
//                ?.loadAd(AdRequest.Builder().build())
//        }, 800)
    }

    private fun hintSystem(answer: String) {

        val imm =
            view?.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)

        val hinttext = view?.findViewById<TextView>(R.id.hintbal)
        var hintBalance = extractDigits(hinttext?.text.toString())

        if (hintBalance > 0) {

            HintDialog.show(context, object : HintDialog.HintDialogInterface {
                override fun onYesClickListener(dialog: Dialog?) {
                    hintBalance--
                    saveHint(hintBalance)
                    hinttext?.text = "Hint: $hintBalance"
                    answerReveal(answer)

                }

                override fun onNoClickListener(dialog: Dialog?) {

                }


            }, "Wanna use hint?")


        } else {
            Toast.makeText(context, "Watch video to earn hint", Toast.LENGTH_LONG).show()
        }

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

    fun stopStopwatch() {
        isRunning = false
        handler.removeCallbacks(updateTime)
    }

    fun resetStopwatch() {
        isRunning = false
        elapsedTime = 0
        updateText(view?.findViewById(R.id.timer)!!)
    }

    private fun getHintBalance(): Int? {
        val sharedPreferences =
            context?.getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)

        return sharedPreferences?.getInt("hintBalanceKey", 20)

    }

    private fun saveHint(hintBalance: Int) {
        val sharedPreferences =
            context?.getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()

        editor?.putInt("hintBalanceKey", hintBalance)

        editor?.apply()
    }

    private fun updateText(textViewStopwatch: TextView) {
        val seconds = (elapsedTime / 1000).toInt()
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val remainingSeconds = seconds % 60

        val timeFormatted = String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds)


        textViewStopwatch.text = timeFormatted
    }

    fun startStopwatch() {
        isRunning = true
        startTime = SystemClock.elapsedRealtime()
        handler.postDelayed(updateTime, 0)
    }


    private val updateTime: Runnable = object : Runnable {
        override fun run() {
            if (isRunning) {
                val now = SystemClock.elapsedRealtime()
                elapsedTime += now - startTime
                startTime = now
                updateText(view?.findViewById(R.id.timer)!!)
                handler.postDelayed(this, 1000)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        stopStopwatch()
    }

    private fun initializeLevel(
        answer: String,
        editTexts: List<EditText>,
        nextLevel: Int,
        view: View
    ) {

        val hint = getHintBalance()
        view.findViewById<TextView>(R.id.hintbal).text = "Hint: $hint"

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                if (before > 0) {
                    handleBackspacePress(charSequence, start)
                }
            }

            override fun onTextChanged(
                charSequence: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                if (count == 1) {
                    if (checkAnswer(editTexts) == answer) {

                        var hintbal = getHintBalance()
                        hintbal = hintbal!! + 3
                        saveHint(hintbal)


                        val inputMethodManager =
                            editTexts[0].context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        inputMethodManager.hideSoftInputFromWindow(editTexts[0].windowToken, 0)
                        var levelList = getLevelList("levels")
                        levelList = levelList + nextLevel
                        saveLevelList("levels", levelList)
                        var level = nextLevel - 1
                        var timearray = IntArray(3)
                        timearray =
                            splitTime(view?.findViewById<TextView>(R.id.timer)?.text.toString()!!)

                        if (timearray[0] == 0 && timearray[1] == 0 && timearray[2] != 0) {
                            level = 3
                        } else if (timearray[0] == 0 && timearray[1] <= 3 && timearray[2] != 0) {
                            level = 2
                        } else {
                            level = 1
                        }


                        com.sro.guessit.Dialog.Dialog.show(
                            context,
                            level,
                            activity,
                            object : com.sro.guessit.Dialog.Dialog.CustomDialogInterface {
                                override fun onPositiveButtonClick(
                                    dialog: com.sro.guessit.Dialog.Dialog?,
                                    text: String?,
                                    redStar: ImageView?,
                                    yellowStar: ImageView?,
                                    greenStar: ImageView?
                                ) {

                                }

                            })

                    } else {
                        if (isNotNullEditext(editTexts)) {
                            Toast.makeText(context, "Wrong Answer", Toast.LENGTH_SHORT).show()
                        }
                    }

                    // Set focus to the next EditText
                    for (i in 0 until editTexts.size - 1) {
                        if (charSequence.hashCode() == editTexts[i].text.hashCode()) {
                            editTexts[i + 1].requestFocus()
                            break
                        }
                    }
                }
            }

            override fun afterTextChanged(editable: Editable) {
                // No need to do anything here
            }

            private fun handleBackspacePress(charSequence: CharSequence, start: Int) {
                if (start == 0) {
                    // Move focus to the previous EditText
                    for (i in 1 until editTexts.size) {
                        if (charSequence.hashCode() == editTexts[i].text.hashCode()) {
                            editTexts[i - 1].requestFocus()
                            break
                        }
                    }
                }
            }
        }

        // Add text watchers to all EditTexts
        for (i in 0 until editTexts.size) {
            editTexts[i].addTextChangedListener(textWatcher)
        }
    }


    private fun initializeLevel50(
        answer: String,
        editTexts: MutableList<EditText>,
        nextLevel: Int,
        view: View
    ) {

        val hint = getHintBalance()
        view.findViewById<TextView>(R.id.hintbal).text = "Hint: $hint"

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                if (before > 0) {
                    // Handle backspace press
                    handleBackspacePress(charSequence, start)
                }
            }

            override fun onTextChanged(
                charSequence: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                if (count == 1) {
                    if (checkAnswer(editTexts) == answer) {
                        var hintbal = getHintBalance()
                        hintbal = hintbal!! + 5
                        saveHint(hintbal)
                        val inputMethodManager =
                            editTexts[0].context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        inputMethodManager.hideSoftInputFromWindow(editTexts[0].windowToken, 0)
                        var levelList = getLevelList("levels")
                        levelList = levelList + nextLevel
                        saveLevelList("levels", levelList)
                        var level = nextLevel - 1
                        var timearray = IntArray(3)
                        timearray =
                            splitTime(view?.findViewById<TextView>(R.id.timer)?.text.toString()!!)

                        if (timearray[0] == 0 && timearray[1] == 0 && timearray[2] != 0) {
                            level = 3
                        } else if (timearray[0] == 0 && timearray[1] <= 3 && timearray[2] != 0) {
                            level = 2
                        } else {
                            level = 1
                        }


                        HintDialog.show(context, object : HintDialog.HintDialogInterface {
                            override fun onYesClickListener(dialog: Dialog?) {
                                openPlayStore(context!!)
                            }

                            override fun onNoClickListener(dialog: Dialog?) {
                            }
                        }, "Do you like it? Rate us")


                    } else {
                        if (isNotNullEditext(editTexts)) {
                            Toast.makeText(context, "Wrong Answer", Toast.LENGTH_SHORT).show()
                        }
                    }

                    // Set focus to the next EditText
                    for (i in 0 until editTexts.size - 1) {
                        if (charSequence.hashCode() == editTexts[i].text.hashCode()) {
                            editTexts[i + 1].requestFocus()
                            break
                        }
                    }
                }
            }

            override fun afterTextChanged(editable: Editable) {
                // No need to do anything here
            }

            private fun handleBackspacePress(charSequence: CharSequence, start: Int) {
                if (start == 0) {
                    // Move focus to the previous EditText
                    for (i in 1 until editTexts.size) {
                        if (charSequence.hashCode() == editTexts[i].text.hashCode()) {
                            editTexts[i - 1].requestFocus()
                            break
                        }
                    }
                }
            }
        }

        // Add text watchers to all EditTexts
        for (i in 0 until editTexts.size) {
            editTexts[i].addTextChangedListener(textWatcher)
        }


    }

    fun openPlayStore(context: Context) {
        val appPackageName = context.packageName
        try {
            context.startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName"))
            )
        } catch (e: android.content.ActivityNotFoundException) {
            // If the Play Store app is not available, open the Play Store website
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                )
            )
        }
    }

    private fun answerReveal(answer: String) {


        val hintLayout: RelativeLayout = view?.findViewById(R.id.hintl)!!
        val letters = hintLayout.findViewById<TextView>(R.id.letters)
        val hinttext = view?.findViewById<TextView>(R.id.hintbal)

        hintLayout.isVisible = true

        val revealAnswer = hintLayout.findViewById<TextView>(R.id.revealanswer)

        revealAnswer.setOnClickListener {

            HintDialog.show(context, object : HintDialog.HintDialogInterface {
                override fun onYesClickListener(dialog: Dialog?) {
                    var hintBalance = extractDigits(hinttext?.text.toString())
                    if (hintBalance > 0) {
                        hintBalance--
                        saveHint(hintBalance)
                        hinttext?.text = "Hint: $hintBalance"

                        revealAnswer.text = answer

                    } else {
                        Toast.makeText(context, "Earn hint by watching video", Toast.LENGTH_LONG)
                            .show()
                    }

                }

                override fun onNoClickListener(dialog: Dialog?) {

                }


            }, "1 hint will be used")
        }

        letters.text = shuffleString(answer)

    }

    fun shuffleString(input: String): String {
        val givenLetters = input.toCharArray().toList()
        val random = Random()

        // Create a list of all alphabets excluding the given letters
        val remainingLetters = ('A'..'Z').toSet() - givenLetters.toSet()

        // Create a list of all alphabets, including the given letters
        val allLettersList = givenLetters.toMutableList()

        // Fill in the remaining slots with random letters until the total length is greater than the given string by 2
        while (allLettersList.size <= input.length + 2) {
            val randomLetter = remainingLetters.random()
            allLettersList.add(randomLetter)
        }

        // Shuffle the combined list using Fisher-Yates shuffle
        for (i in allLettersList.size - 1 downTo 1) {
            val j = random.nextInt(i + 1)
            val temp = allLettersList[i]
            allLettersList[i] = allLettersList[j]
            allLettersList[j] = temp
        }

        // Convert the list back to a string and return
        return allLettersList.joinToString(" ")
    }


    fun splitTime(text: String): IntArray {
        val timeComponents = text.split(":").toTypedArray()
        val timeIntArray = IntArray(3)

        // Ensure that there are at least 3 components (hours, minutes, seconds)
        if (timeComponents.size >= 3) {
            timeIntArray[0] = timeComponents[0].toIntOrNull() ?: 0 // Hours
            timeIntArray[1] = timeComponents[1].toIntOrNull() ?: 0 // Minutes
            timeIntArray[2] = timeComponents[2].toIntOrNull() ?: 0 // Seconds
        }

        return timeIntArray
    }

    private fun isNotNullEditext(editTexts: List<EditText>): Boolean {

        for (i in editTexts) {
            if (i.text.toString() == "")
                return false
        }

        return true
    }

    private fun checkAnswer(editTexts: List<EditText>): String {

        for (i in editTexts) {
            return if (i.text.toString() == "")
                ""
            else {
                var word = ""

                for (i in editTexts) {
                    word += i.text.toString()
                }
                word
            }
        }
        return ""
    }

    private fun getLevelList(key: String): List<Int> {

        val jsonString = context?.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            ?.getString(key, "")
        return Gson().fromJson(jsonString, object : TypeToken<List<Int>>() {}.type) ?: emptyList()
    }

    fun saveLevelList(key: String, list: List<Int>) {
        val jsonString = Gson().toJson(list)
        context?.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)?.edit()
            ?.putString(key, jsonString)?.apply()
    }

    companion object {
        private val ARG_LEVEL = "level"
        public var currentLevel: Int = 0

        fun newInstance(level: Int): GameFragement? {
            val fragment = GameFragement()
            val args = Bundle()
            args.putInt(ARG_LEVEL, level)
            fragment.setArguments(args)
            return fragment
        }

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GameFragement().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}