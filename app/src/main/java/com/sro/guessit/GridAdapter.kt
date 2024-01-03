package com.sro.guessit

import android.content.Context
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GridAdapter(
    private val context: Context,
    private val data: List<Int>,
    private val levelList: List<Int>
) :
    BaseAdapter() {

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertedView = convertView

        if (convertedView == null) {
            val inflater = LayoutInflater.from(context)
            convertedView = inflater.inflate(R.layout.grid_item, parent, false)
        }

        val textView = convertedView!!.findViewById<TextView>(R.id.levelnumber)
        val lock = convertedView.findViewById<ImageView>(R.id.lock)
        textView.text = data[position].toString()


        convertedView.isClickable = !levelList.contains(position + 1)
        lock.isVisible = !levelList.contains(position + 1)

        return convertedView
    }

    private fun getLevelList(): List<Int> {
        val jsonString =
            context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).getString("levels", "")
        return Gson().fromJson(jsonString, object : TypeToken<List<Int>>() {}.type) ?: emptyList()
    }
}
