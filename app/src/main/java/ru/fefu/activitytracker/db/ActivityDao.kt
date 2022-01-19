package ru.fefu.activitytracker.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ActivityDao {
    @Query("SELECT * FROM activities ORDER BY date_end DESC")
    fun getAll(): LiveData<List<Activities>>

    @Insert
    fun insert(activity: Activities)
}