package com.sro.guessit.Dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.sro.guessit.R

class HintDialog {
    interface HintDialogInterface {
        fun onYesClickListener(dialog: Dialog?)
        fun onNoClickListener(dialog: Dialog?)
    }

    companion object {
        fun show(context: Context?, listener: HintDialogInterface, title: String) {
            val builder = AlertDialog.Builder(context)
            val inflater = LayoutInflater.from(context)
            val dialogView: View = inflater.inflate(R.layout.hintdialog, null)
            builder.setView(dialogView)

            dialogView.setBackgroundColor(ContextCompat.getColor(context!!, R.color.yellow))

            val dialog = builder.create()
            val yes = dialogView.findViewById<TextView>(R.id.yes)
            val no = dialogView.findViewById<TextView>(R.id.no)
            val textview = dialogView.findViewById<TextView>(R.id.titleName)

            textview.text = title
            yes.setOnClickListener {
                listener.onYesClickListener(builder.create())
                dialog.dismiss()
            }

            no.setOnClickListener {
                listener.onNoClickListener(dialog)
                dialog.dismiss()
            }

            dialog.show()
        }


    }
}
