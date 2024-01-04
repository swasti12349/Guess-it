package com.sro.guessit

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
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
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Job
import java.util.Random
import java.util.regex.Matcher
import java.util.regex.Pattern


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
        var view: View
        handler = Handler()
        startStopwatch()

        when (currentLevel) {
            1 -> {
                view = inflater.inflate(R.layout.fragment_game_level1, container, false)
                initializeMobilAds(view)

                list = mutableListOf<EditText>(
                    view.findViewById(R.id.ed1),
                    view.findViewById(R.id.ed2),
                    view.findViewById(R.id.ed3),
                    view.findViewById(R.id.ed4)
                )

                view.findViewById<ImageView>(R.id.hint).setOnClickListener {
                    hintSystem("CAKE")
                }
                initializeLevel("CAKE", list, 2, view)
//                adSystem(view)


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
                initializeMobilAds(view)

                view.findViewById<ImageView>(R.id.hint).setOnClickListener {
                    hintSystem("BRUSH")
                }
                initializeLevel("BRUSH", list, 3, view)

            }

            3 -> {
                view = inflater.inflate(R.layout.fragment_game_level3, container, false)
                list = mutableListOf<EditText>(
                    view.findViewById(R.id.ed1),
                    view.findViewById(R.id.ed2),
                    view.findViewById(R.id.ed3),
                    view.findViewById(R.id.ed4)
                )
                initializeMobilAds(view)

                view.findViewById<ImageView>(R.id.hint).setOnClickListener {
                    hintSystem("MOON")
                }
                initializeLevel("MOON", list, 4, view)
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

                view.findViewById<ImageView>(R.id.hint).setOnClickListener {
                    hintSystem("GULABJAMUN")
                }
                initializeLevel("GULABJAMUN", list, 5, view)
            }

            5 -> {
                view = inflater.inflate(R.layout.fragment_game_level5, container, false)
                list = mutableListOf<EditText>(
                    view.findViewById(R.id.ed1),
                    view.findViewById(R.id.ed2),
                    view.findViewById(R.id.ed3),
                    view.findViewById(R.id.ed4)
                )

                view.findViewById<ImageView>(R.id.hint).setOnClickListener {
                    hintSystem("ECHO")
                }
                initializeLevel("ECHO", list, 6, view)
            }

            6 -> {
                view = inflater.inflate(R.layout.fragment_game_level6, container, false)
                list = mutableListOf<EditText>(
                    view.findViewById(R.id.ed1),
                    view.findViewById(R.id.ed2),
                    view.findViewById(R.id.ed3),
                    view.findViewById(R.id.ed4)
                )
                view.findViewById<ImageView>(R.id.hint).setOnClickListener {
                    hintSystem("FIRE")
                }
                initializeLevel("FIRE", list, 7, view)
            }

            7 -> {
                view = inflater.inflate(R.layout.fragment_game_level7, container, false)
                list = mutableListOf<EditText>(
                    view.findViewById(R.id.ed1),
                    view.findViewById(R.id.ed2),
                    view.findViewById(R.id.ed3)
                )
                view.findViewById<ImageView>(R.id.hint).setOnClickListener {
                    hintSystem("MAP")
                }
                initializeLevel("MAP", list, 8, view)
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
                view.findViewById<ImageView>(R.id.hint).setOnClickListener {
                    hintSystem("KEYBOARD")
                }
                initializeLevel("KEYBOARD", list, 9, view)
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
                view.findViewById<ImageView>(R.id.hint).setOnClickListener {
                    hintSystem("SQUASH")
                }
                initializeLevel("SQUASH", list, 10, view)


            }

            10 -> {
                view = inflater.inflate(R.layout.fragment_game_level10, container, false)
                list = mutableListOf<EditText>(
                    view.findViewById(R.id.ed1),
                    view.findViewById(R.id.ed2),
                    view.findViewById(R.id.ed3),
                    view.findViewById(R.id.ed4),
                    view.findViewById(R.id.ed5)
                )
                view.findViewById<ImageView>(R.id.hint).setOnClickListener {
                    hintSystem("CLOUD")
                }
                initializeLevel("CLOUD", list, 11, view)
            }


            11 -> {
                view = inflater.inflate(R.layout.fragment_game_level11, container, false)
                list = mutableListOf<EditText>(
                    view.findViewById(R.id.ed1),
                    view.findViewById(R.id.ed2),
                    view.findViewById(R.id.ed3),
                    view.findViewById(R.id.ed4),
                    view.findViewById(R.id.ed5)
                )
                view.findViewById<ImageView>(R.id.hint).setOnClickListener {
                    hintSystem("PIANO")
                }
                initializeLevel("PIANO", list, 12, view)
            }

            12 -> {
                view = inflater.inflate(R.layout.fragment_game_level12, container, false)
                list = mutableListOf<EditText>(
                    view.findViewById(R.id.ed1),
                    view.findViewById(R.id.ed2),
                    view.findViewById(R.id.ed3),
                    view.findViewById(R.id.ed4),
                    view.findViewById(R.id.ed5),
                    view.findViewById(R.id.ed6)
                )
                view.findViewById<ImageView>(R.id.hint).setOnClickListener {
                    hintSystem("COFFIN")
                }
                initializeLevel("COFFIN", list, 13, view)
            }

            13 -> {
                view = inflater.inflate(R.layout.fragment_game_level13, container, false)
                list = mutableListOf<EditText>(
                    view.findViewById(R.id.ed1),
                    view.findViewById(R.id.ed2),
                    view.findViewById(R.id.ed3),
                    view.findViewById(R.id.ed4),
                    view.findViewById(R.id.ed5),
                    view.findViewById(R.id.ed6)
                )
                view.findViewById<ImageView>(R.id.hint).setOnClickListener {
                    hintSystem("CANDLE")
                }
                initializeLevel("CANDLE", list, 14, view)
            }

            14 -> {
                view = inflater.inflate(R.layout.fragment_game_level14, container, false)
                list = mutableListOf<EditText>(
                    view.findViewById(R.id.ed1),
                    view.findViewById(R.id.ed2),
                    view.findViewById(R.id.ed3),
                    view.findViewById(R.id.ed4),
                    view.findViewById(R.id.ed5),
                    view.findViewById(R.id.ed6),
                    view.findViewById(R.id.ed7)
                )
                view.findViewById<ImageView>(R.id.hint).setOnClickListener {
                    hintSystem("PROMISE")
                }
                initializeLevel("PROMISE", list, 15, view)
            }

            15 -> {
                view = inflater.inflate(R.layout.fragment_game_level15, container, false)
                list = mutableListOf<EditText>(
                    view.findViewById(R.id.ed1),
                    view.findViewById(R.id.ed2),
                    view.findViewById(R.id.ed3),
                    view.findViewById(R.id.ed4),
                    view.findViewById(R.id.ed5),
                    view.findViewById(R.id.ed6)
                )
                view.findViewById<ImageView>(R.id.hint).setOnClickListener {
                    hintSystem("CHEESE")
                }
                initializeLevel("CHEESE", list, 16, view)
            }

            16 -> {
                view = inflater.inflate(R.layout.fragment_game_level16, container, false)
                list = mutableListOf<EditText>(
                    view.findViewById(R.id.ed1),
                    view.findViewById(R.id.ed2),
                    view.findViewById(R.id.ed3),
                    view.findViewById(R.id.ed4)
                )
                view.findViewById<ImageView>(R.id.hint).setOnClickListener {
                    hintSystem("NAIL")
                }
                initializeLevel("NAIL", list, 17, view)
            }

            17 -> {
                view = inflater.inflate(R.layout.fragment_game_level17, container, false)
                list = mutableListOf<EditText>(
                    view.findViewById(R.id.ed1),
                    view.findViewById(R.id.ed2),
                    view.findViewById(R.id.ed3),
                    view.findViewById(R.id.ed4),
                    view.findViewById(R.id.ed5),
                    view.findViewById(R.id.ed6),
                )
                view.findViewById<ImageView>(R.id.hint).setOnClickListener {
                    hintSystem("POCKET")
                }
                initializeLevel("POCKET", list, 18, view)
            }

            18 -> {
                view = inflater.inflate(R.layout.fragment_game_level18, container, false)
                list = mutableListOf<EditText>(
                    view.findViewById(R.id.ed1),
                    view.findViewById(R.id.ed2),
                    view.findViewById(R.id.ed3),
                    view.findViewById(R.id.ed4),
                    view.findViewById(R.id.ed5)
                )
                view.findViewById<ImageView>(R.id.hint).setOnClickListener {
                    hintSystem("QUEUE")
                }
                initializeLevel("QUEUE", list, 19, view)
            }

            19 -> {
                view = inflater.inflate(R.layout.fragment_game_level19, container, false)
                list = mutableListOf<EditText>(
                    view.findViewById(R.id.ed1),
                    view.findViewById(R.id.ed2),
                    view.findViewById(R.id.ed3),
                    view.findViewById(R.id.ed4),
                    view.findViewById(R.id.ed5)
                )
                view.findViewById<ImageView>(R.id.hint).setOnClickListener {
                    hintSystem("WRONG")
                }
                initializeLevel("WRONG", list, 20, view)
            }

            20 -> {
                view = inflater.inflate(R.layout.fragment_game_level20, container, false)
                list = mutableListOf<EditText>(
                    view.findViewById(R.id.ed1),
                    view.findViewById(R.id.ed2),
                    view.findViewById(R.id.ed3),
                    view.findViewById(R.id.ed4),
                    view.findViewById(R.id.ed5),
                    view.findViewById(R.id.ed6),
                    view.findViewById(R.id.ed7)
                )
                view.findViewById<ImageView>(R.id.hint).setOnClickListener {
                    hintSystem("LOUNGER")
                }
                initializeLevel("LOUNGER", list, 21, view)
            }

            21 -> {
                view = inflater.inflate(R.layout.fragment_game_level21, container, false)
                list = mutableListOf<EditText>(
                    view.findViewById(R.id.ed1),
                    view.findViewById(R.id.ed2),
                    view.findViewById(R.id.ed3),
                    view.findViewById(R.id.ed4),
                    view.findViewById(R.id.ed5)
                )
                view.findViewById<ImageView>(R.id.hint).setOnClickListener {
                    hintSystem("GRAPE")
                }
                initializeLevel("GRAPE", list, 22, view)
            }

            22 -> {
                view = inflater.inflate(R.layout.fragment_game_level22, container, false)
                list = mutableListOf<EditText>(
                    view.findViewById(R.id.ed1),
                    view.findViewById(R.id.ed2),
                    view.findViewById(R.id.ed3),
                    view.findViewById(R.id.ed4),
                    view.findViewById(R.id.ed5),
                    view.findViewById(R.id.ed6),
                    view.findViewById(R.id.ed7)
                )
                view.findViewById<ImageView>(R.id.hint).setOnClickListener {
                    hintSystem("NOTHING")
                }
                initializeLevel("NOTHING", list, 23, view)
            }

            23 -> {
                view = inflater.inflate(R.layout.fragment_game_level23, container, false)
                list = mutableListOf<EditText>(
                    view.findViewById(R.id.ed1),
                    view.findViewById(R.id.ed2),
                    view.findViewById(R.id.ed3),
                    view.findViewById(R.id.ed4)
                )
                view.findViewById<ImageView>(R.id.hint).setOnClickListener {
                    hintSystem("BANK")
                }
                initializeLevel("BANK", list, 24, view)
            }

            24 -> {
                view = inflater.inflate(R.layout.fragment_game_level24, container, false)
                list = mutableListOf<EditText>(
                    view.findViewById(R.id.ed1),
                    view.findViewById(R.id.ed2),
                    view.findViewById(R.id.ed3),
                    view.findViewById(R.id.ed4),
                    view.findViewById(R.id.ed5)
                )
                view.findViewById<ImageView>(R.id.hint).setOnClickListener {
                    hintSystem("SWIMS")
                }
                initializeLevel("SWIMS", list, 25, view)
            }

            25 -> {
                view = inflater.inflate(R.layout.fragment_game_level25, container, false)
                list = mutableListOf<EditText>(
                    view.findViewById(R.id.ed1),
                    view.findViewById(R.id.ed2),
                    view.findViewById(R.id.ed3),
                    view.findViewById(R.id.ed4)
                )
                view.findViewById<ImageView>(R.id.hint).setOnClickListener {
                    hintSystem("BOOK")
                }
                initializeLevel("BOOK", list, 26, view)
            }

            26 -> {
                view = inflater.inflate(R.layout.fragment_game_level26, container, false)
                list = mutableListOf<EditText>(
                    view.findViewById(R.id.ed1),
                    view.findViewById(R.id.ed2),
                    view.findViewById(R.id.ed3),
                    view.findViewById(R.id.ed4),
                    view.findViewById(R.id.ed5),
                    view.findViewById(R.id.ed6)
                )
                view.findViewById<ImageView>(R.id.hint).setOnClickListener {
                    hintSystem("SHADOW")
                }
                initializeLevel("SHADOW", list, 27, view)
            }

            27 -> {
                view = inflater.inflate(R.layout.fragment_game_level27, container, false)
                list = mutableListOf<EditText>(
                    view.findViewById(R.id.ed1),
                    view.findViewById(R.id.ed2),
                    view.findViewById(R.id.ed3),
                    view.findViewById(R.id.ed4),
                    view.findViewById(R.id.ed5),
                    view.findViewById(R.id.ed6),
                )
                view.findViewById<ImageView>(R.id.hint).setOnClickListener {
                    hintSystem("BREATH")
                }
                initializeLevel("BREATH", list, 28, view)
            }

            28 -> {
                view = inflater.inflate(R.layout.fragment_game_level28, container, false)
                list = mutableListOf<EditText>(
                    view.findViewById(R.id.ed1),
                    view.findViewById(R.id.ed2),
                    view.findViewById(R.id.ed3),
                    view.findViewById(R.id.ed4),
                    view.findViewById(R.id.ed5)
                )
                view.findViewById<ImageView>(R.id.hint).setOnClickListener {
                    hintSystem("SHIRT")
                }
                initializeLevel("SHIRT", list, 29, view)
            }

            29 -> {
                view = inflater.inflate(R.layout.fragment_game_level29, container, false)
                list = mutableListOf<EditText>(
                    view.findViewById(R.id.ed1),
                    view.findViewById(R.id.ed2),
                    view.findViewById(R.id.ed3),
                    view.findViewById(R.id.ed4)
                )
                view.findViewById<ImageView>(R.id.hint).setOnClickListener {
                    hintSystem("DARK")
                }
                initializeLevel("DARK", list, 30, view)
            }

            30 -> {
                view = inflater.inflate(R.layout.fragment_game_level30, container, false)
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
                view.findViewById<ImageView>(R.id.hint).setOnClickListener {
                    hintSystem("MOSQUITO")
                }
                initializeLevel("MOSQUITO", list, 31, view)
            }

            31 -> {
                view = inflater.inflate(R.layout.fragment_game_level31, container, false)
                list = mutableListOf<EditText>(
                    view.findViewById(R.id.ed1),
                    view.findViewById(R.id.ed2),
                    view.findViewById(R.id.ed3),
                    view.findViewById(R.id.ed4),
                    view.findViewById(R.id.ed5),
                    view.findViewById(R.id.ed6)
                )
                view.findViewById<ImageView>(R.id.hint).setOnClickListener {
                    hintSystem("DOCTOR")
                }
                initializeLevel("DOCTOR", list, 32, view)
            }

            32 -> {
                view = inflater.inflate(R.layout.fragment_game_level32, container, false)
                list = mutableListOf<EditText>(
                    view.findViewById(R.id.ed1),
                    view.findViewById(R.id.ed2),
                    view.findViewById(R.id.ed3),
                    view.findViewById(R.id.ed4),
                    view.findViewById(R.id.ed5),
                    view.findViewById(R.id.ed6),
                )
                view.findViewById<ImageView>(R.id.hint).setOnClickListener {
                    hintSystem("MIRROR")
                }
                initializeLevel("MIRROR", list, 33, view)
            }

            33 -> {
                view = inflater.inflate(R.layout.fragment_game_level33, container, false)
                list = mutableListOf<EditText>(
                    view.findViewById(R.id.ed1),
                    view.findViewById(R.id.ed2),
                    view.findViewById(R.id.ed3)
                )
                view.findViewById<ImageView>(R.id.hint).setOnClickListener {
                    hintSystem("AGE")
                }
                initializeLevel("AGE", list, 34, view)
            }

            34 -> {
                view = inflater.inflate(R.layout.fragment_game_level34, container, false)
                list = mutableListOf<EditText>(
                    view.findViewById(R.id.ed1),
                    view.findViewById(R.id.ed2),
                    view.findViewById(R.id.ed3),
                    view.findViewById(R.id.ed4),
                    view.findViewById(R.id.ed5),
                    view.findViewById(R.id.ed6),
                    view.findViewById(R.id.ed7)
                )
                view.findViewById<ImageView>(R.id.hint).setOnClickListener {
                    hintSystem("DUSTBIN")
                }
                initializeLevel("DUSTBIN", list, 35, view)
            }

            35 -> {
                view = inflater.inflate(R.layout.fragment_game_level35, container, false)
                list = mutableListOf<EditText>(
                    view.findViewById(R.id.ed1),
                    view.findViewById(R.id.ed2),
                    view.findViewById(R.id.ed3),
                    view.findViewById(R.id.ed4)
                )
                view.findViewById<ImageView>(R.id.hint).setOnClickListener {
                    hintSystem("HOLE")
                }
                initializeLevel("HOLE", list, 36, view)
            }

            36 -> {
                view = inflater.inflate(R.layout.fragment_game_level36, container, false)
                list = mutableListOf<EditText>(
                    view.findViewById(R.id.ed1),
                    view.findViewById(R.id.ed2),
                    view.findViewById(R.id.ed3)
                )
                view.findViewById<ImageView>(R.id.hint).setOnClickListener {
                    hintSystem("KEY")
                }
                initializeLevel("KEY", list, 37, view)
            }

            37 -> {
                view = inflater.inflate(R.layout.fragment_game_level37, container, false)
                list = mutableListOf<EditText>(
                    view.findViewById(R.id.ed1),
                    view.findViewById(R.id.ed2),
                    view.findViewById(R.id.ed3),
                    view.findViewById(R.id.ed4),
                    view.findViewById(R.id.ed5)
                )
                view.findViewById<ImageView>(R.id.hint).setOnClickListener {
                    hintSystem("CLOCK")
                }
                initializeLevel("CLOCK", list, 38, view)
            }

            38 -> {
                view = inflater.inflate(R.layout.fragment_game_level38, container, false)
                list = mutableListOf<EditText>(
                    view.findViewById(R.id.ed1),
                    view.findViewById(R.id.ed2),
                    view.findViewById(R.id.ed3),
                    view.findViewById(R.id.ed4),
                    view.findViewById(R.id.ed5),
                    view.findViewById(R.id.ed6)
                )
                view.findViewById<ImageView>(R.id.hint).setOnClickListener {
                    hintSystem("SPONGE")
                }
                initializeLevel("SPONGE", list, 39, view)
            }

            39 -> {
                view = inflater.inflate(R.layout.fragment_game_level39, container, false)
                list = mutableListOf<EditText>(
                    view.findViewById(R.id.ed1),
                    view.findViewById(R.id.ed2),
                    view.findViewById(R.id.ed3),
                    view.findViewById(R.id.ed4),
                    view.findViewById(R.id.ed5),
                    view.findViewById(R.id.ed6),
                    view.findViewById(R.id.ed7)
                )
                view.findViewById<ImageView>(R.id.hint).setOnClickListener {
                    hintSystem("FLORIDA")
                }
                initializeLevel("FLORIDA", list, 40, view)
            }

            40 -> {
                view = inflater.inflate(R.layout.fragment_game_level40, container, false)
                list = mutableListOf<EditText>(
                    view.findViewById(R.id.ed1),
                    view.findViewById(R.id.ed2),
                    view.findViewById(R.id.ed3),
                    view.findViewById(R.id.ed4),
                    view.findViewById(R.id.ed5),
                )
                view.findViewById<ImageView>(R.id.hint).setOnClickListener {
                    hintSystem("TOWEL")
                }
                initializeLevel("TOWEL", list, 41, view)
            }

            41 -> {
                view = inflater.inflate(R.layout.fragment_game_level41, container, false)
                list = mutableListOf<EditText>(
                    view.findViewById(R.id.ed1),
                    view.findViewById(R.id.ed2),
                    view.findViewById(R.id.ed3),
                    view.findViewById(R.id.ed4),
                    view.findViewById(R.id.ed5)
                )
                view.findViewById<ImageView>(R.id.hint).setOnClickListener {
                    hintSystem("RIVER")
                }
                initializeLevel("RIVER", list, 42, view)
            }

            42 -> {
                view = inflater.inflate(R.layout.fragment_game_level42, container, false)
                list = mutableListOf<EditText>(
                    view.findViewById(R.id.ed1),
                    view.findViewById(R.id.ed2),
                    view.findViewById(R.id.ed3),
                    view.findViewById(R.id.ed4),
                    view.findViewById(R.id.ed5),
                    view.findViewById(R.id.ed6)
                )
                view.findViewById<ImageView>(R.id.hint).setOnClickListener {
                    hintSystem("NORWAY")
                }
                initializeLevel("NORWAY", list, 43, view)
            }

            43 -> {
                view = inflater.inflate(R.layout.fragment_game_level43, container, false)
                list = mutableListOf<EditText>(
                    view.findViewById(R.id.ed1),
                    view.findViewById(R.id.ed2),
                    view.findViewById(R.id.ed3),
                    view.findViewById(R.id.ed4),
                    view.findViewById(R.id.ed5)
                )
                view.findViewById<ImageView>(R.id.hint).setOnClickListener {
                    hintSystem("OCEAN")
                }
                initializeLevel("OCEAN", list, 44, view)
            }

            44 -> {
                view = inflater.inflate(R.layout.fragment_game_level44, container, false)
                list = mutableListOf<EditText>(
                    view.findViewById(R.id.ed1),
                    view.findViewById(R.id.ed2),
                    view.findViewById(R.id.ed3),
                    view.findViewById(R.id.ed4),
                    view.findViewById(R.id.ed5),
                    view.findViewById(R.id.ed6),
                )
                view.findViewById<ImageView>(R.id.hint).setOnClickListener {
                    hintSystem("STAMPS")
                }
                initializeLevel("STAMPS", list, 45, view)
            }

            45 -> {
                view = inflater.inflate(R.layout.fragment_game_level45, container, false)
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
                )
                view.findViewById<ImageView>(R.id.hint).setOnClickListener {
                    hintSystem("HONEYCOMB")
                }
                initializeLevel("HONEYCOMB", list, 46, view)
            }

            46 -> {
                view = inflater.inflate(R.layout.fragment_game_level46, container, false)
                list = mutableListOf<EditText>(
                    view.findViewById(R.id.ed1),
                    view.findViewById(R.id.ed2),
                    view.findViewById(R.id.ed3),
                    view.findViewById(R.id.ed4),
                    view.findViewById(R.id.ed5),
                    view.findViewById(R.id.ed6),
                    view.findViewById(R.id.ed7)
                )
                view.findViewById<ImageView>(R.id.hint).setOnClickListener {
                    hintSystem("LETTUCE")
                }
                initializeLevel("LETTUCE", list, 47, view)
            }

            47 -> {
                view = inflater.inflate(R.layout.fragment_game_level47, container, false)
                list = mutableListOf<EditText>(
                    view.findViewById(R.id.ed1),
                    view.findViewById(R.id.ed2),
                    view.findViewById(R.id.ed3),
                    view.findViewById(R.id.ed4)
                )

                view.findViewById<ImageView>(R.id.hint).setOnClickListener {
                    hintSystem("JEEP")
                }
                initializeLevel("JEEP", list, 48, view)
            }

            48 -> {
                view = inflater.inflate(R.layout.fragment_game_level48, container, false)
                list = mutableListOf<EditText>(
                    view.findViewById(R.id.ed1),
                    view.findViewById(R.id.ed2),
                    view.findViewById(R.id.ed3),
                    view.findViewById(R.id.ed4),
                    view.findViewById(R.id.ed5),
                    view.findViewById(R.id.ed6),
                )
                view.findViewById<ImageView>(R.id.hint).setOnClickListener {
                    hintSystem("BALLON")
                }
                initializeLevel("BALLON", list, 49, view)
            }

            49 -> {
                view = inflater.inflate(R.layout.fragment_game_level49, container, false)
                list = mutableListOf<EditText>(
                    view.findViewById(R.id.ed1),
                    view.findViewById(R.id.ed2),
                    view.findViewById(R.id.ed3),
                    view.findViewById(R.id.ed4)
                )
                view.findViewById<ImageView>(R.id.hint).setOnClickListener {
                    hintSystem("TREE")
                }
                initializeLevel("TREE", list, 50, view)
            }

            else -> {
                view = inflater.inflate(R.layout.fragment_game_level50, container, false)
                list = mutableListOf<EditText>(
                    view.findViewById(R.id.ed1),
                    view.findViewById(R.id.ed2),
                    view.findViewById(R.id.ed3),
                    view.findViewById(R.id.ed4),
                    view.findViewById(R.id.ed5)
                )
                view.findViewById<ImageView>(R.id.hint).setOnClickListener {
                    hintSystem("INDIA")
                }
                initializeLevel50("INDIA", list, 0, view)
            }
        }

        return view
    }

    private fun initializeMobilAds(view: View?) {
        Handler().postDelayed({
            MobileAds.initialize(requireContext())
            view?.findViewById<AdView>(R.id.adView)
                ?.loadAd(AdRequest.Builder().build())
        }, 1000)


    }

    override fun onResume() {
        super.onResume()

    }


    private fun hintSystem(answer: String) {

        val imm =
            view?.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)

        val hinttext = view?.findViewById<TextView>(R.id.hintbal)
        var hintBalance = extractDigits(hinttext?.text.toString())

        if (hintBalance > 0) {

            HintDialog.show(context, object : HintDialog.HintDialogInterface {
                override fun onYesClickListener(dialog: android.app.Dialog?) {
                    hintBalance--
                    saveHint(hintBalance)
                    hinttext?.text = "Hint: $hintBalance"
                    answerReveal(answer)

                }

                override fun onNoClickListener(dialog: android.app.Dialog?) {

                }


            }, "Wanna use hint?")


        } else {
            Toast.makeText(context, "Watch video to earn hint", Toast.LENGTH_LONG).show()
        }

    }

    private fun extractDigits(input: String): Int {
        // Define a pattern to match digits in the input string
        val pattern: Pattern = Pattern.compile("\\d+")

        // Create a matcher object to find matches in the input string
        val matcher: Matcher = pattern.matcher(input)

        // Check if any matches are found
        return if (matcher.find()) {
            // Extract and parse the matched digits
            val digits = matcher.group()
            digits.toInt()
        } else {
            // No digits found in the input string
            println("No digits found in the input string.")
            -1 // Or any suitable value to indicate no digits found
        }
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

    private fun getHintBalance(): Int? {
        val sharedPreferences =
            context?.getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)

        return sharedPreferences?.getInt("hintBalanceKey", 8)

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


    private fun initializeLevel(
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
                        } else if (timearray[0] == 0 && timearray[1] <= 3 && timearray[2] != 0) {
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
                        } else if (timearray[0] == 0 && timearray[1] <= 3 && timearray[2] != 0) {
                            level = 2
                        } else {
                            level = 1
                        }


                        HintDialog.show(context, object : HintDialog.HintDialogInterface {
                            override fun onYesClickListener(dialog: android.app.Dialog?) {
                                openPlayStore(context!!)
                            }

                            override fun onNoClickListener(dialog: android.app.Dialog?) {
                            }
                        }, "Do you like it?")


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
                override fun onYesClickListener(dialog: android.app.Dialog?) {
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

                override fun onNoClickListener(dialog: android.app.Dialog?) {

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