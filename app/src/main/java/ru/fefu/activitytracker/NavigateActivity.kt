package ru.fefu.activitytracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView


class NavigateActivity : AppCompatActivity() {
    private var bottomNavigation: BottomNavigationView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigate)

        bottomNavigation = findViewById(R.id.bottom_navigation)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().apply {
                add(
                    R.id.activity_container,
                    CollectionAdapterFragment.newInstance(
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



    private fun bottomNavigationSelector() {
        bottomNavigation?.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.activity_menu -> {
                    val profileFragment =
                        supportFragmentManager.findFragmentByTag("profile_fragment")
                    supportFragmentManager.beginTransaction().apply {
                        add(
                            R.id.activity_container,
                            CollectionAdapterFragment.newInstance(
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