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
    private var isRunning = false
    private lateinit var handler: Handler

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
        stopwatchJob = Job()
        handler = Handler()
        when (currentLevel) {
            1 -> {
                view = inflater.inflate(R.layout.fragment_game_level1, container, false)
                list = mutableListOf<EditText>(
                    view.findViewById(R.id.ed1),
                    view.findViewById(R.id.ed2),
                    view.findViewById(R.id.ed3),
                    view.findViewById(R.id.ed4)
                )
                initializeLevel("CAKE", list)
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
                initializeLevel("BRUSH", list)
            }

            3 -> {
                view = inflater.inflate(R.layout.fragment_game_level3, container, false)
                list = mutableListOf<EditText>(
                    view.findViewById(R.id.ed1),
                    view.findViewById(R.id.ed2),
                    view.findViewById(R.id.ed3),
                    view.findViewById(R.id.ed4)
                )
                initializeLevel("MOON", list)
            }

            4 -> {
                view = inflater.inflate(R.layout.fragment_game_level4, container, false)
                initializeLevel4(view)
            }

            5 -> {
                view = inflater.inflate(R.layout.fragment_game_level5, container, false)
                initializeLevel5(view)
            }

            6 -> {
                view = inflater.inflate(R.layout.fragment_game_level6, container, false)
                initializeLevel6(view)
            }

            else -> {
                view = inflater.inflate(R.layout.fragment_game_level50, container, false)
                initializeLevel50(view)
            }
        }

        return view
    }

    fun resetStopwatch(view: TextView) {
        isRunning = false
        elapsedTime = 0
        updateText(view)
    }

    private fun updateText(textViewStopwatch: TextView) {
        val seconds = (elapsedTime / 1000).toInt()
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        val timeFormatted = String.format("%02d:%02d", minutes, remainingSeconds)
        textViewStopwatch.text = timeFormatted
    }

    fun startStopwatch(view: View) {
        if (isRunning) {
            // Stopwatch is already running, stop it
            isRunning = false
            handler.removeCallbacks(updateTime)
        } else {
            // Stopwatch is not running, start it
            isRunning = true
            handler.postDelayed(updateTime, 0)
        }
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

    private fun initializeLevel50(view: View?) {

    }

    private fun initializeLevel6(view: View?) {

    }

    private fun initializeLevel5(view: View) {

    }

    private fun initializeLevel4(view: View) {

    }

    private fun initializeLevel3(view: View) {

    }

    private fun initializeLevel2(view: View) {


        startStopwatch(view)

        var n = 0
        val answer = "BRUSH"
        var currentWord = ""
        val editText1: EditText = view.findViewById(R.id.ed1)
        val editText2: EditText = view.findViewById(R.id.ed2)
        val editText3: EditText = view.findViewById(R.id.ed3)
        val editText4: EditText = view.findViewById(R.id.ed4)
        val editText5: EditText = view.findViewById(R.id.ed5)

        val editTexts = mutableListOf(editText1, editText2, editText3, editText4, editText5)

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
                    val upperCaseChar = charSequence.toString().toUpperCase()
                    currentWord += upperCaseChar

                    for (i in n until editTexts.size - 1) {
                        n = i + 1
                        if ((i + 1) < answer.length && editTexts[i + 1].text.toString() == "") {
                            editTexts[i + 1].requestFocus()
                            if (currentWord == answer) {
                                handleCorrectAnswer(view)
                            }
                            break
                        } else {
                            currentWord += editTexts[i + 1].text.toString()
                            if (currentWord == answer) {
                                handleCorrectAnswer(view)
                            }
                        }
                    }
                }
            }

            override fun afterTextChanged(editable: Editable) {
                if (currentWord == answer) {
                    handleCorrectAnswer(view)
                }
            }

            private fun handleBackspacePress(charSequence: CharSequence, start: Int) {
                if (start == 0) {
                    for (i in 1 until editTexts.size) {
                        if (charSequence.hashCode() == editTexts[i].text.hashCode() && editTexts[i - 1].text.toString() == "") {
                            editTexts[i - 1].requestFocus()
                            break
                        }
                    }
                }
            }

            private fun handleCorrectAnswer(view: View) {
                val inputMethodManager =
                    view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)

                var levelList = getLevelList("levels")
                levelList = levelList + 3
                saveLevelList("levels", levelList)

                Dialog.show(context, object : Dialog.CustomDialogInterface {

                    override fun onPositiveButtonClick(
                        dialog: android.app.Dialog?,
                        text: String?,
                        redStar: ImageView?,
                        yellowStar: ImageView?,
                        greenStar: ImageView?
                    ) {
                        redStar?.visibility = View.VISIBLE
                    }

                })
            }
        }

        editText1.addTextChangedListener(textWatcher)
        editText2.addTextChangedListener(textWatcher)
        editText3.addTextChangedListener(textWatcher)
        editText4.addTextChangedListener(textWatcher)
        editText5.addTextChangedListener(textWatcher)
    }


    private fun updateTextView() {
        val seconds = elapsedTime / 1000
        val minutes = seconds / 60
        val hours = minutes / 60

        val formattedTime = String.format("%02d:%02d:%02d", hours, minutes % 60, seconds % 60)
     }



    private fun initializeLevel(answer: String, editTexts: MutableList<EditText>) {
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

                        var levelList = getLevelList("levels")
                        levelList = levelList + 2
                        saveLevelList("levels", levelList)

                        Dialog.show(context, object : Dialog.CustomDialogInterface {
                            override fun onPositiveButtonClick(
                                dialog: android.app.Dialog?,
                                text: String?,
                                redStar: ImageView?,
                                yellowStar: ImageView?,
                                greenStar: ImageView?
                            ) {
                                redStar?.visibility = View.VISIBLE
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