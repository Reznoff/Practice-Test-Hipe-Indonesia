package com.pedro.weatherforecast.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.pedro.weatherforecast.ui.today.TodayFragment
import com.pedro.weatherforecast.ui.weekly.WeeklyFragment

class ViewPagerAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(pos: Int): Fragment {
        when (pos) {
            0 -> return TodayFragment()
            1 -> return WeeklyFragment()
        }
        return Fragment()
    }
    override fun getCount(): Int = 2


}