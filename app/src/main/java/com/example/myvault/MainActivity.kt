package com.example.myvault

import android.os.Binder
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.myvault.databinding.ActivityMainBinding
import com.example.myvault.databinding.FragmentImagesBinding
import com.google.android.material.tabs.TabLayout
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage


class MainActivity : AppCompatActivity() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tabLayout = findViewById(R.id.tablayout)
        viewPager = findViewById(R.id.viewpager)

        tabLayout.addTab(tabLayout.newTab().setText("PDF"))
        tabLayout.addTab(tabLayout.newTab().setText("IMAGES"))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = Adapter(
            this, supportFragmentManager,
            tabLayout.tabCount
        )
        viewPager.adapter = adapter

        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.currentItem = tab!!.position
                }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
        }
    }





