package com.example.skyj.Post

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.skyj.R
import kotlinx.android.synthetic.main.apply_item.view.*
import kotlinx.android.synthetic.main.postlist_item.view.*

class ApplyListAdapter(val context: Context,
                       val list_writer : ArrayList<String>,
                       val list_text : ArrayList<String>,
                       val list_email : ArrayList<String>) : BaseAdapter() {
    override fun getView(position: Int, converView: View?, parent: ViewGroup?): View {

        val view: View = LayoutInflater.from(context).inflate(R.layout.apply_item, null)


        view.apply_text.text = list_text.get(position)
        view.apply_writer.text = list_writer.get(position)
        view.apply_email.text = list_email.get(position)

        return view
    }

    override fun getItem(p0: Int): Any {
        return 0
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return list_writer.size
    }
}