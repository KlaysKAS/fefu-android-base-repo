package ru.fefu.activitytracker.gps

import ru.fefu.activitytracker.db.entity.Coordinates
import kotlin.math.*

const val R: Int = 6371
const val DEGREE: Double = Math.PI / 180.0

class CoordToDistance {
    companion object {
        fun getDistanceFromLatLonInM(coord: List<Coordinates>): Double {
            if (coord.size <= 1) {
                return 0.0
            }
            var distance: Double = 0.0
            for (i in 1 until coord.size) {
                val dLat = (coord[i].latitude - coord[i - 1].latitude) * DEGREE
                val dLon = (coord[i].longitude - coord[i - 1].longitude) * DEGREE
                val a = abs(sin(dLat / 2) * sin(dLat / 2) +
                        cos(coord[i].latitude * DEGREE) *
                        cos(coord[i].longitude * DEGREE) *
                        sin(dLon / 2) * sin(dLon / 2))
                val c = 2 * atan2(sqrt(a), sqrt(1 - a))
                val d = R * c * 1000 // Distance in m
                distance += d
            }
            return distance
        }

        fun getDistanceFromLatLonInM(c1: Coordinates, c2: Coordinates): Double {
            val dLat = (c2.latitude - c1.latitude) * DEGREE
            val dLon = (c2.longitude - c1.longitude) * DEGREE
            val a = abs(sin(dLat / 2) * sin(dLat / 2) +
                    cos(c2.latitude * DEGREE) *
                    cos(c2.longitude * DEGREE) *
                    sin(dLon / 2) * sin(dLon / 2))
            val c = 2 * atan2(sqrt(a), sqrt(1 - a))
            val d = R * c * 1000 // Distance in m
            return d
        }
    }
}