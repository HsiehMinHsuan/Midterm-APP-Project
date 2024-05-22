package com.example.assignment

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class MyAdapter(context: Context, data: ArrayList<Agent>, private val layout: Int) : ArrayAdapter<Agent>(context, layout, data) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view = View.inflate(parent.context, layout, null)

        val agent = getItem(position) ?: return view

        val img = view.findViewById<ImageView>(R.id.img_player1)
        img.setImageResource(agent.photo)

        val tv_msg = view.findViewById<TextView>(R.id.tv_player1)
        tv_msg.text = agent.name

        return view
    }
}

