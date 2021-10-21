package ru.fefu.activitytracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TableLayout
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout


class NavigateActivity : AppCompatActivity() {
    private var bottomNavigation: BottomNavigationView? = null

    private var isCreated: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigate)

        bottomNavigation = findViewById(R.id.bottom_navigation)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().apply {
                add(
                    R.id.activity_container,
                    ActivityFragment.newInstance(
                        "Start activityFragment",
                        "activity_fragment"
                    ),
                    "activity_fragment"
                )
                commit()
            }
        }

        bottomNavigationSelector()

    }

    override fun onStart() {
        super.onStart()

        if (!isCreated) {
            val tabLayout = findViewById<TabLayout>(R.id.activity_tab_layout)
            val pager = findViewById<ViewPager2>(R.id.activity_pager)
            val adapter = ActivitiesCollections(supportFragmentManager, lifecycle)
            pager.adapter = adapter

            tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_layout_activity_my))
            tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_layout_activity_users))

            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.let { pager.currentItem = it.position }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }
            })

            pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    tabLayout.selectTab(tabLayout.getTabAt(position))
                }
            })
            isCreated = true
        }
    }

    private fun bottomNavigationSelector() {
        bottomNavigation?.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.activity_menu -> {
                    val profileFragment =
                        supportFragmentManager.findFragmentByTag("profile_fragment")
                    supportFragmentManager.beginTransaction().apply {
                        add(
                            R.id.activity_container,
                            ActivityFragment.newInstance(
                                "Start activityFragment",
                                "activity_fragment"
                            ),
                            "activity_fragment"
                        )
                        if (profileFragment != null)
                            hide(profileFragment)
                        commit()
                    }
                    true
                }
                R.id.profile_menu -> {
                    val activityFragment =
                        supportFragmentManager.findFragmentByTag("activity_fragment")
                    supportFragmentManager.beginTransaction().apply {
                        add(
                            R.id.activity_container,
                            ProfileFragment.newInstance(
                                "Start profileFragment",
                                "profile_fragment"
                            ),
                            "profile_fragment"
                        )
                        if (activityFragment != null)
                            hide(activityFragment)
                        commit()
                    }
                    true
                }
                else ->
                    false
            }
        }
    }
}