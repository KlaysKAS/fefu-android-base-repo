package ru.fefu.activitytracker.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import ru.fefu.activitytracker.db.entity.Activities
import ru.fefu.activitytracker.db.entity.Coordinates

// TODO(MAKE MIGRATION)

@Database(entities = [Activities::class, Coordinates::class], version = 2)
abstract class MyDB: RoomDatabase() {
    abstract fun activityDao(): ActivityDao
}