package com.sro.guessit.Fragment

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sro.guessit.Activity.LevelActivity
import com.sro.guessit.Adapter.GridAdapter
import com.sro.guessit.Dialog.HintDialog
import com.sro.guessit.R
import com.sro.guessit.databinding.FragmentLevelBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LevelFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LevelFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var levels: ArrayList<Int>
    lateinit var binding: FragmentLevelBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var levelList: List<Int>
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
        // Inflate the layout for this fragment
        levels = ArrayList()
        sharedPreferences = context?.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)!!

        for (i in 1..50) {
            levels.add(i)
        }

        levelList = getLevelList("levels")

        binding = FragmentLevelBinding.inflate(layoutInflater, container, false)

        val adapter = GridAdapter(requireContext(), levels, levelList)
        binding.levelsGV.adapter = adapter

        binding.levelsGV.setOnItemClickListener { parent, view, position, id ->
            val selectedLevel = levels[position]

            val bundle = Bundle()
            bundle.putInt("selectedLevel", selectedLevel)
            val fragmentB = GameFragement()
            fragmentB.arguments = bundle

            getFragmentManager()?.beginTransaction()
                ?.replace(R.id.gameContainer, fragmentB)
                ?.addToBackStack(null)
                ?.commit();

        }

        if (levelList.size % 8 == 0) {
            HintDialog.show(context, object : HintDialog.HintDialogInterface {
                override fun onYesClickListener(dialog: Dialog?) {
                    LevelActivity.openPlayStore(requireContext())
                }

                override fun onNoClickListener(dialog: Dialog?) {
                }
            }, "Do you like it? Rate us")
        }
        return binding.root


    }

    private fun getLevelList(key: String): List<Int> {
        val jsonString = sharedPreferences.getString(key, "")
        return Gson().fromJson(jsonString, object : TypeToken<List<Int>>() {}.type) ?: emptyList()
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

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LevelFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}