package ru.fefu.activitytracker.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Activities(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "username") val userName: String,
    @ColumnInfo(name = "activity_type") val type: Int,
    @ColumnInfo(name = "date_start") val dateStart: Long,
    @ColumnInfo(name = "date_end") val dateEnd: Long,
    @ColumnInfo(name = "latitude") val latitude: Double,
    @ColumnInfo(name = "longitude") val longitude: Double,
)