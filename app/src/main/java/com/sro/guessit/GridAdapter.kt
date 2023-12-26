package com.sro.guessit

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class GridAdapter(
    private val context: Context,
    private val data: List<String>,
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
        textView.text = data[position]

        convertedView.isClickable = !levelList.contains(position + 1)

        return convertedView
    }
}
