package com.sro.guessit

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView

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
        fun show(context: Context?, level: Int, listener: CustomDialogInterface) {
            val builder = AlertDialog.Builder(context)
            val inflater = LayoutInflater.from(context)
            val dialogView: View = inflater.inflate(R.layout.dialogsuccess, null)
            builder.setView(dialogView)

            val redStar = dialogView.findViewById<ImageView>(R.id.redstar)
            val yellowStar = dialogView.findViewById<ImageView>(R.id.yellowstar)
            val greenStar = dialogView.findViewById<ImageView>(R.id.greenstar)
            val nextLevelBtn = dialogView.findViewById<ImageView>(R.id.nextlevel)

            // Set star visibility based on the level
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
                context?.startActivity(Intent(context, LevelActivity::class.java))
            }

            builder.setTitle(" ")
            builder.create().show()
        }
    }
}
