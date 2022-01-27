package ru.fefu.activitytracker.gps

import android.location.Location
import android.location.LocationListener

class LocListener: LocationListener {
    private var locListenerInterface: LocListenerInterface? = null

    override fun onLocationChanged(p0: Location) {
        locListenerInterface?.onLocationChanged(p0)
    }

    fun setLocListenerI(loc: LocListenerInterface) {
        locListenerInterface = loc
    }
}