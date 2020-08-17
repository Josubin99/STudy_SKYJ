package com.example.skyj

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
import com.example.skyj.StudyFragment.FragmentAdapter
import com.google.android.material.tabs.TabLayout
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_study.*
import kotlinx.android.synthetic.main.custom_tab.view.*

class StudyActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_study)

        val fragmentAdapter=FragmentAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        list_viewpager.adapter=fragmentAdapter

        tab_layout.addTab(tab_layout.newTab().setCustomView(createTabView("면접")))
        tab_layout.addTab(tab_layout.newTab().setCustomView(createTabView("오픽")))
        tab_layout.addTab(tab_layout.newTab().setCustomView(createTabView("공모전")))
        tab_layout.addTab(tab_layout.newTab().setCustomView(createTabView("토익")))
        tab_layout.addTab(tab_layout.newTab().setCustomView(createTabView("NCS")))
        tab_layout.addTab(tab_layout.newTab().setCustomView(createTabView("기사")))

        list_viewpager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_layout))

        tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if(tab!=null) {
                    list_viewpager.currentItem = tab.position
                }
            }

        })

    }

    private fun createTabView(tabName: String) : View {

        val tabView=LayoutInflater.from(baseContext).inflate(R.layout.custom_tab, null)

        tabView.txt_name.text=tabName

        return tabView
    }
}