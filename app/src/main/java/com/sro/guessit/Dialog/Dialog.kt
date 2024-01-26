package com.sro.guessit.Dialog

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.sro.guessit.Fragment.GameFragement
import com.sro.guessit.Fragment.LevelFragment
import com.sro.guessit.R


class Dialog {

    interface CustomDialogInterface {
        fun onPositiveButtonClick(
            dialog: Dialog?,
            text: String?,
            redStar: ImageView?,
            yellowStar: ImageView?,
            greenStar: ImageView?
        )
    }

    companion object {
        fun show(
            context: Context?,
            level: Int,
            fragmentActivity: FragmentActivity?,
            listener: CustomDialogInterface
        ) {
            val builder = AlertDialog.Builder(context)
            val inflater = LayoutInflater.from(context)
            GameFragement.currentLevel++

            val dialogView: View = inflater.inflate(R.layout.dialogsuccess, null)
            builder.setView(dialogView)
            MediaPlayer.create(context, R.raw.levcomp).start()

            val redStar = dialogView.findViewById<ImageView>(R.id.redstar)
            val yellowStar = dialogView.findViewById<ImageView>(R.id.yellowstar)
            val greenStar = dialogView.findViewById<ImageView>(R.id.greenstar)
            val nextLevelBtn = dialogView.findViewById<ImageView>(R.id.nextlevel)
            val dialog = builder.create()

            when (level) {
                1 -> {
                    redStar.visibility = View.VISIBLE
                    yellowStar.visibility = View.GONE
                    greenStar.visibility = View.GONE
                }

                2 -> {
                    redStar.visibility = View.VISIBLE
                    yellowStar.visibility = View.VISIBLE
                    greenStar.visibility = View.GONE
                }

                3 -> {
                    redStar.visibility = View.VISIBLE
                    yellowStar.visibility = View.VISIBLE
                    greenStar.visibility = View.VISIBLE
                }
            }

            nextLevelBtn.setOnClickListener {
//                context?.startActivity(Intent(context, LevelActivity::class.java))
                var transaction: FragmentTransaction =
                    fragmentActivity?.getSupportFragmentManager()?.beginTransaction()
                        ?.replace(R.id.gameContainer, LevelFragment())!!
                transaction.commit()
                dialog.dismiss()

//                val gameFragment: GameFragement = GameFragement.newInstance(level)!!
//
//                val transaction: FragmentTransaction =
//                    fragmentActivity?.getSupportFragmentManager()?.beginTransaction()
//                        ?.replace(R.id.gameContainer, LevelFragment())!!
//                transaction.replace(R.id.gameContainer, gameFragment)
//                transaction.commit()
//                dialog.dismiss()

            }
            dialog.show()
        }
    }
}
