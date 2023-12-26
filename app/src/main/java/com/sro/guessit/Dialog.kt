package com.sro.guessit

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView


class Dialog {

    interface CustomDialogInterface {
        fun onPositiveButtonClick(dialog: Dialog?, text: String?, redStar:ImageView?,
                                  yellowStar:ImageView?,
                                  greenStar:ImageView?)

    }

    companion object {
        fun show(context: Context?, listener: CustomDialogInterface) {
            val builder = AlertDialog.Builder(context)
            val inflater = LayoutInflater.from(context)
            val dialogView: View = inflater.inflate(R.layout.dialogsuccess, null)
            builder.setView(dialogView)
            val redstar = dialogView.findViewById<ImageView>(R.id.redstar)
            val yellow = dialogView.findViewById<ImageView>(R.id.yellowstar)
            val green = dialogView.findViewById<ImageView>(R.id.greenstar)
            val nextlevelbtn = dialogView.findViewById<ImageView>(R.id.nextlevel)

            nextlevelbtn.setOnClickListener {

                context?.startActivity(Intent(context, LevelActivity::class.java))
            }

            builder.setTitle(" ")
            builder.create().show()
        }
    }
}