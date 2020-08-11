package com.example.skyj.StudyFragment

import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.skyj.R

class FragmentAdapter(fm : FragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT: Int) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
    override fun getItem(position: Int): Fragment {
        return when(position) {
            0-> {
                InterviewFragment()
            }
            1-> {
                OpicFragment()
            }
            2-> {
                ContestFragment()
            }
            3-> {
                ToiecFragment()
            }
            4-> {
                NcsFragment()
            }
            else-> {
                return EngineerFragment()
            }
        }
    }
    override fun getCount(): Int {
        return 6
    }

}

