package ru.fefu.activitytracker.db

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.fefu.activitytracker.db.entity.Activities
import ru.fefu.activitytracker.db.entity.ActivityAndCoordinates
import ru.fefu.activitytracker.db.entity.Coordinates

@Dao
interface ActivityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(activity: Activities)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCoordinates(coordinates: Coordinates)

    @Transaction
    @Query("SELECT * FROM activities WHERE date_end IS NOT NULL ORDER BY date_end DESC")
    fun getAll(): LiveData<List<ActivityAndCoordinates>>

    @Query("SELECT id FROM activities ORDER BY id DESC LIMIT 1")
    fun getLastActivity(): Long?

    @Query("UPDATE activities SET date_end = :dateEnd WHERE id=:id")
    fun finishActivity(dateEnd: Long, id: Long)

    @Query("DELETE FROM activities WHERE date_end IS NULL")
    fun dropUnfinishedActivities()

    @Query("SELECT * FROM activities WHERE id = :id")
    fun getActivityById(id: Int): ActivityAndCoordinates

    @Query("DELETE FROM coordinates")
    fun dropCoordinates()

    @Query("SELECT * FROM coordinates WHERE activity_id = :id ORDER BY coordinate_id")
    fun getCoords(id: Long): LiveData<List<Coordinates>>

    @Query("DELETE FROM activities WHERE 1")
    fun dropAllActivity()

    @Query("SELECT id FROM activities WHERE date_end IS NULL ORDER BY id DESC LIMIT 1")
    fun getIdUnfinishActivity(): LiveData<Long?>
}