package com.sro.guessit.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.sro.guessit.Activity.GameActivity
import com.sro.guessit.Activity.GuessTheGameActivity
import com.sro.guessit.Activity.LevelActivity
import com.sro.guessit.Model.listModel
import com.sro.guessit.R
//
//class CatAdapter(
//    context: Context,
//    listItem: MutableList<listModel>,
//    resource: Int,
//    objects: List<String>
//) :
//    ArrayAdapter<String>(context, resource, objects) {
//
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        val itemView = inflater.inflate(R.layout.catlistitem, parent, false)
//
//        val textViewItem: TextView = itemView.findViewById(R.id.catname)
//        textViewItem.text = getItem(position)
//        val currentItem = getItem(position)
//        itemView.setOnClickListener{
//
//            if (currentItem=="Guess the Word"){
//                context.startActivity(Intent(context, GameActivity::class.java))
//            }else{
//                context.startActivity(Intent(context, GuessTheGameActivity::class.java))
//
//             }
//
//        }
//
//        return itemView
//    }
//
//
//}



class CatAdapter(context: Context, private val data: List<listModel>) :
    ArrayAdapter<listModel>(context, R.layout.catlistitem, data) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        val viewHolder: ViewHolder

        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.catlistitem, parent, false)

            viewHolder = ViewHolder()
            viewHolder.imageView = itemView.findViewById(R.id.imageView)
            viewHolder.textView = itemView.findViewById(R.id.textView)

            itemView.tag = viewHolder
        } else {
            viewHolder = itemView.tag as ViewHolder
        }

        // Set the data for the current item
        var currentItem = getItem(position)
        viewHolder.imageView.setImageResource(currentItem!!.imageResource)
        viewHolder.textView.text = currentItem.text

        itemView?.setOnClickListener{
            if (currentItem.text=="Word"){
                context.startActivity(Intent(context, GameActivity::class.java))
            }else if(currentItem.text=="Game"){
                context.startActivity(Intent(context, GuessTheGameActivity::class.java))
            }
        }
        return itemView!!
    }

    // ViewHolder pattern to improve performance by avoiding unnecessary calls to findViewById
    private class ViewHolder {
        lateinit var imageView: ImageView
        lateinit var textView: TextView
    }
}
