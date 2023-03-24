package com.example.myvault

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import fragment.Images
import fragment.PDF

internal class Adapter(var content: Context, fm: FragmentManager, var totalTabs: Int): FragmentPagerAdapter(fm) {


    override fun getItem(position: Int): Fragment {
        return when(position){
            0->{
                PDF()
            }
            1->{
                Images()
            }
            else-> getItem(position)
        }
    }
    override fun getCount(): Int {
        return totalTabs
    }
}