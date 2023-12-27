package com.sro.guessit

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.text.method.MultiTapKeyListener
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.w3c.dom.Text


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class GameFragement : Fragment() {
    private var currentLevel: Int = 0
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
        currentLevel = requireArguments().getInt(ARG_LEVEL)
        val view: View
        handler = Handler()
        startStopwatch()
        when (currentLevel) {
            1 -> {
                view = inflater.inflate(R.layout.fragment_game_level1, container, false)
                list = mutableListOf<EditText>(
                    view.findViewById(R.id.ed1),
                    view.findViewById(R.id.ed2),
                    view.findViewById(R.id.ed3),
                    view.findViewById(R.id.ed4)
                )
                initializeLevel("CAKE", list, 2)
            }

            2 -> {
                view = inflater.inflate(R.layout.fragment_game_level2, container, false)
                list = mutableListOf<EditText>(
                    view.findViewById(R.id.ed1),
                    view.findViewById(R.id.ed2),
                    view.findViewById(R.id.ed3),
                    view.findViewById(R.id.ed4),
                    view.findViewById(R.id.ed5),
                )
                initializeLevel("BRUSH", list, 3)
            }

            3 -> {
                view = inflater.inflate(R.layout.fragment_game_level3, container, false)
                list = mutableListOf<EditText>(
                    view.findViewById(R.id.ed1),
                    view.findViewById(R.id.ed2),
                    view.findViewById(R.id.ed3),
                    view.findViewById(R.id.ed4)
                )
                initializeLevel("MOON", list, 4)
            }

            4 -> {
                view = inflater.inflate(R.layout.fragment_game_level4, container, false)
                list = mutableListOf<EditText>(
                    view.findViewById(R.id.ed1),
                    view.findViewById(R.id.ed2),
                    view.findViewById(R.id.ed3),
                    view.findViewById(R.id.ed4),
                    view.findViewById(R.id.ed5),
                    view.findViewById(R.id.ed6),
                    view.findViewById(R.id.ed7),
                    view.findViewById(R.id.ed8),
                    view.findViewById(R.id.ed9),
                    view.findViewById(R.id.ed10)
                )
                initializeLevel("GULABJAMUN", list, 5)
            }

            5 -> {
                view = inflater.inflate(R.layout.fragment_game_level5, container, false)
                list = mutableListOf<EditText>(
                    view.findViewById(R.id.ed1),
                    view.findViewById(R.id.ed2),
                    view.findViewById(R.id.ed3),
                    view.findViewById(R.id.ed4)
                )
                initializeLevel("ECHO", list, 6)
            }

            6 -> {
                view = inflater.inflate(R.layout.fragment_game_level6, container, false)
                list = mutableListOf<EditText>(
                    view.findViewById(R.id.ed1),
                    view.findViewById(R.id.ed2),
                    view.findViewById(R.id.ed3),
                    view.findViewById(R.id.ed4)
                )
                initializeLevel("FIRE", list, 7)
            }

            7 -> {
                view = inflater.inflate(R.layout.fragment_game_level7, container, false)
                list = mutableListOf<EditText>(
                    view.findViewById(R.id.ed1),
                    view.findViewById(R.id.ed2),
                    view.findViewById(R.id.ed3)
                )
                initializeLevel("MAP", list, 8)
            }

            8 -> {
                view = inflater.inflate(R.layout.fragment_game_level8, container, false)
                list = mutableListOf<EditText>(
                    view.findViewById(R.id.ed1),
                    view.findViewById(R.id.ed2),
                    view.findViewById(R.id.ed3),
                    view.findViewById(R.id.ed4),
                    view.findViewById(R.id.ed5),
                    view.findViewById(R.id.ed6),
                    view.findViewById(R.id.ed7),
                    view.findViewById(R.id.ed8)
                )
                initializeLevel("KEYBOARD", list, 9)
            }

            9 -> {
                view = inflater.inflate(R.layout.fragment_game_level9, container, false)
                list = mutableListOf<EditText>(
                    view.findViewById(R.id.ed1),
                    view.findViewById(R.id.ed2),
                    view.findViewById(R.id.ed3),
                    view.findViewById(R.id.ed4),
                    view.findViewById(R.id.ed5),
                    view.findViewById(R.id.ed6)
                )
                initializeLevel("SQUASH", list, 10)
            }


            else -> {
                view = inflater.inflate(R.layout.fragment_game_level50, container, false)
                initializeLevel50(view)
            }
        }

        return view
    }

    private fun initializeLevel50(view: View?) {

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

    private fun updateText(textViewStopwatch: TextView) {
        val seconds = (elapsedTime / 1000).toInt()
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val remainingSeconds = seconds % 60

        val timeFormatted = String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds)


        textViewStopwatch.text = timeFormatted
    }


    fun starSystem() {

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
        resetStopwatch()
    }

    


    private fun initializeLevel(answer: String, editTexts: MutableList<EditText>, nextLevel: Int) {

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
                        val inputMethodManager =
                            editTexts[0].context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        inputMethodManager.hideSoftInputFromWindow(editTexts[0].windowToken, 0)
                        stopStopwatch()
                        var levelList = getLevelList("levels")
                        levelList = levelList + nextLevel
                        saveLevelList("levels", levelList)
                        var level = nextLevel - 1
                        var timearray = IntArray(3)
                        timearray =
                            splitTime(view?.findViewById<TextView>(R.id.timer)?.text.toString()!!)

                        if (timearray[0] == 0 && timearray[1] == 0 && timearray[2] != 0) {
                            level = 3
                        } else if (timearray[0] == 0 && timearray[1] <=3 && timearray[2] != 0) {
                            level = 2
                        } else {
                            level = 1
                        }


                        Dialog.show(context, level, object : Dialog.CustomDialogInterface {
                            override fun onPositiveButtonClick(
                                dialog: Dialog?,
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

    private fun isNotNullEditext(editTexts: MutableList<EditText>): Boolean {

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