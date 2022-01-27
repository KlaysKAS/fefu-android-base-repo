package ru.fefu.activitytracker.db.entity

import androidx.room.*

@Entity
data class Activities(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "username") val userName: String,
    @ColumnInfo(name = "activity_type") val type: Int,
    @ColumnInfo(name = "date_start") val dateStart: Long,
    @ColumnInfo(name = "date_end") val dateEnd: Long? = null,
)


@Entity
data class Coordinates(
    @PrimaryKey(autoGenerate = true) val coordinate_id: Int,
    @ColumnInfo(name = "activity_id") val activityId: Int,
    val longitude: Double,
    val latitude: Double,
)

// One-to-Many relationship
data class ActivityAndCoordinates(
    @Embedded val activity: Activities,
    @Relation(
        parentColumn = "id",
        entityColumn = "activity_id",
    )
    val coordinates: List<Coordinates>
)