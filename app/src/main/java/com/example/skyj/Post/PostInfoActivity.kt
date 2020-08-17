package com.example.skyj.Post

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import com.example.skyj.R
import kotlinx.android.synthetic.main.activity_post_info.*

class PostInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_info)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_area, ContentFragment())
            .commit()

        postInfo_tab1.setOnClickListener {

            postInfo_tab1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18F)
            postInfo_tab2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15F)

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_area, ContentFragment())
                .commit()
        }

        postInfo_tab2.setOnClickListener {

            postInfo_tab1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15F)
            postInfo_tab2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18F)

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_area, ApplyFragment())
                .commit()
        }
    }
}