package com.example.skyj

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.skyj.Auth.LoginActivity
import com.example.skyj.Auth.MyPageActivity
import com.example.skyj.StudyFragment.FragmentAdapter
import com.example.skyj.StudyFragment.InterviewFragment
import com.example.skyj.StudyFragment.NcsFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_study.*
import kotlinx.android.synthetic.main.menu_bar.*

class MainActivity : AppCompatActivity() {

    internal lateinit var viewPager: ViewPager

    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth= FirebaseAuth.getInstance()

        val img=arrayOf(
            R.drawable.interview,
            R.drawable.opic,
            R.drawable.contest,
            R.drawable.toiec,
            R.drawable.ncs,
            R.drawable.engineer
        )

        val text=arrayOf(
            "면접",
            "오픽",
            "공모전",
            "토익",
            "NCS",
            "기사"
        )

        val gridviewAdapter=GridviewAdapter(this, img, text)
        gridview.adapter=gridviewAdapter

        gridview.setOnItemClickListener { adapterView, view, i, l ->

            val intent = Intent(this, StudyActivity::class.java)
            startActivity(intent)

        }

        viewPager=findViewById(R.id.viewpager) as ViewPager

        val adapter=ViewpagerAdapter(this)
        viewPager.adapter=adapter

        my_page.setOnClickListener {

            if(auth.currentUser==null){
                val intent=Intent(this,LoginActivity::class.java)
                startActivity(intent)

            }else{
                val intent=Intent(this, MyPageActivity::class.java)
                startActivity(intent)

            }

        }
    }
}