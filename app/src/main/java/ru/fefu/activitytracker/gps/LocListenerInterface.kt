package ru.fefu.activitytracker.gps

import android.location.Location

interface LocListenerInterface {
    fun onLocationChanged(loc: Location)
}