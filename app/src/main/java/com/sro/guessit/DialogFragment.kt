package com.sro.guessit

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class DialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(
            requireActivity()
        )

        val inflater = requireActivity().layoutInflater
        val customView: View = inflater.inflate(R.layout.dialogsuccess, null)

        builder.setView(customView)

        val customButton = customView.findViewById<ImageButton>(R.id.nextlevel)

        customButton.setOnClickListener {
            onCustomButtonClick()
        }

        builder.setTitle("Custom Dialog")

        // Create and return the AlertDialog instance
        return builder.create()
    }

    private fun onCustomButtonClick() {


    }
}