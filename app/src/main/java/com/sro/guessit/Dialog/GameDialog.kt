package com.sro.guessit.Dialog

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.sro.guessit.Fragment.GameFragement
import com.sro.guessit.Fragment.LevelFragment
import com.sro.guessit.R


class GameDialog {

    interface CustomDialogInterface {
        fun onPositiveButtonClick(

        )
    }

    companion object {
        fun show(
            context: Context?,
            level: Int,
            listener: CustomDialogInterface,
            titleStr:String
        ) {
            val builder = AlertDialog.Builder(context)
            val inflater = LayoutInflater.from(context)

            val dialogView: View = inflater.inflate(R.layout.dialogsuccess, null)
            builder.setView(dialogView)
            MediaPlayer.create(context, R.raw.levcomp).start()

            val redStar = dialogView.findViewById<ImageView>(R.id.redstar)
            val yellowStar = dialogView.findViewById<ImageView>(R.id.yellowstar)
            val greenStar = dialogView.findViewById<ImageView>(R.id.greenstar)
            val nextLevelBtn = dialogView.findViewById<ImageView>(R.id.nextlevel)
            val title = dialogView.findViewById<TextView>(R.id.title)

            title.text = titleStr

            val dialog = builder.create()
            builder.setCancelable(false)

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
                listener.onPositiveButtonClick()
                dialog.dismiss()
            }
            dialog.show()
        }
    }
}
