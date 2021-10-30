package ru.fefu.activitytracker

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.fefu.activitytracker.MyActivityPackage.MyActivityAdapter


class NavigateActivity : AppCompatActivity(), MyActivityAdapter.OnItemListener {
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
                    CollectionAdapterFragment.newInstance(t),
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
                            CollectionAdapterFragment.newInstance(t),
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

    override fun onItemClick(user: String) {
        val activityFragment =
            supportFragmentManager.findFragmentByTag("activity_fragment")
        val profileFragment =
            supportFragmentManager.findFragmentByTag("profile_fragment")
        supportFragmentManager.beginTransaction().apply {
            add(
                R.id.activity_container,
                DetailActivityInfoFragment.newInstance(t, user),
                "detailed_fragment"
            )
            if (profileFragment != null)
                hide(profileFragment)
            if (activityFragment != null)
                hide(activityFragment)
            commit()
        }
    }

    override fun onItemBack() {
        val activityFragment =
            supportFragmentManager.findFragmentByTag("activity_fragment")
        val profileFragment =
            supportFragmentManager.findFragmentByTag("profile_fragment")
        val detailedFragment =
            supportFragmentManager.findFragmentByTag("detailed_fragment")
        supportFragmentManager.beginTransaction().apply {
            if (detailedFragment != null)
                detach(detailedFragment)
            if (profileFragment != null)
                show(profileFragment)
            if (activityFragment != null)
                show(activityFragment)
            commit()
        }

    }
}