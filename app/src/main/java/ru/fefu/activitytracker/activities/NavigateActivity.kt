package ru.fefu.activitytracker.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.profile.ProfileFragment


class NavigateActivity : AppCompatActivity() {
    private var bottomNavigation: BottomNavigationView? = null
    private val t = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigate)

        bottomNavigation = findViewById(R.id.bottom_navigation)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().apply {
                add(
                    R.id.activity_container,
                    ActivityFlowFragment.newInstance(),
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
                    val activityFragment =
                        supportFragmentManager.findFragmentByTag("activity_fragment")
                    val profileFragment =
                        supportFragmentManager.findFragmentByTag("profile_fragment")
                    supportFragmentManager.beginTransaction().apply {
                        if (activityFragment != null) {
                            show(activityFragment)
                        } else
                        add(
                            R.id.activity_container,
                            ActivityFlowFragment.newInstance(),
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
                    val profileFragment =
                        supportFragmentManager.findFragmentByTag("profile_fragment")
                    supportFragmentManager.beginTransaction().apply {
                        if (profileFragment != null) {
                            show(profileFragment)
                        } else
                        add(
                            R.id.activity_container,
                            ProfileFragment.newInstance(),
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