package com.example.skyj

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager

class ViewpagerAdapter (private val context : Context) : PagerAdapter(){

    private var layoutInflater : LayoutInflater?=null

    val Image=arrayOf(
        R.drawable.softwaremaestro,
        R.drawable.scurpture,
        R.drawable.likelion
    )

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getCount(): Int {
        return Image.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        layoutInflater=context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v= layoutInflater!!.inflate(R.layout.viewpager_activity, null)
        val image=v.findViewById<View>(R.id.imageView) as ImageView

        image.setImageResource(Image[position])

        val vp=container as ViewPager
        vp.addView(v, 0)

        image.setOnClickListener {
            if(position == 0) {

                val intent= Intent(Intent.ACTION_VIEW, Uri.parse("http://swmaestro.org/user/main.do"))
                image.context.startActivity(intent)

            } else if(position==1){

                val intent= Intent(Intent.ACTION_VIEW, Uri.parse("https://www.thinkcontest.com/Contest/ContestDetail.html?id=14229"))
                image.context.startActivity(intent)

            } else{

                val intent= Intent(Intent.ACTION_VIEW, Uri.parse("https://likelion.net/"))
                image.context.startActivity(intent)

            }
        }

        return v
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {

        val vp=container as ViewPager
        val v=`object` as View
        vp.removeView(v)

    }


}