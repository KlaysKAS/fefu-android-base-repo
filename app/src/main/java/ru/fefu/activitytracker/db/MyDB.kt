package ru.fefu.activitytracker.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Activities::class], version = 1)
abstract class MyDB: RoomDatabase() {
    abstract fun activityDao(): ActivityDao
}