package ru.fefu.activitytracker

import android.app.Application
import androidx.room.Room
import ru.fefu.activitytracker.db.MyDB

class App: Application() {
    companion object {
        lateinit var INSTANCE: App
        public var username = ""
    }

    val db: MyDB by lazy {
        Room.databaseBuilder(
            this,
            MyDB::class.java,
            "my_database"
        ).allowMainThreadQueries().build()
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}