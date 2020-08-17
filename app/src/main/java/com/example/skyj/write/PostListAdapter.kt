package com.example.skyj.write

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import com.example.skyj.R
import kotlinx.android.synthetic.main.postlist_item.view.*

class PostListAdapter (val context: Context,
                       val list_writer : ArrayList<String>,
                       val list_title : ArrayList<String>,
                       val list_image : ArrayList<String>,
                       val list_email : ArrayList<String>) : BaseAdapter(){
    override fun getView(position: Int, converView: View?, parent: ViewGroup?): View {

        val view : View = LayoutInflater.from(context).inflate(R.layout.postlist_item, null)


        view.post_writer.text=list_writer.get(position)
        view.post_title.text=list_title.get(position)
        view.post_email.text=list_email.get(position)
        Glide.with(context).load(list_image.get(position)).into(view.findViewById(R.id.post_img))

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