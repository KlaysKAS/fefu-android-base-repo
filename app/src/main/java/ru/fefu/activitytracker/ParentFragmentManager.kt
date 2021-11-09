package ru.fefu.activitytracker

import androidx.fragment.app.FragmentManager

interface ParentFragmentManager {
    fun getActivitiesFragmentManager() : FragmentManager
}