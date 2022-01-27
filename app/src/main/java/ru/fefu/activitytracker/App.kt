package ru.fefu.activitytracker

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.fefu.activitytracker.api.Api
import ru.fefu.activitytracker.api.models.Profile
import ru.fefu.activitytracker.db.MyDB

class App: Application() {
    companion object {
        lateinit var INSTANCE: App
        var profile: Profile? = null

        private lateinit var api: Api
        val getApi: Api
            get() = api

        private lateinit var sharedPref: SharedPreferences
        private const val APP_PREFERENCES = "storage"
        const val APP_PREFERENCES_TOKEN = "token"
        val getSharedPref: SharedPreferences
            get() = sharedPref

        fun getToken(): String {
            var token: String = ""
            if (sharedPref.contains(APP_PREFERENCES_TOKEN)) {
                token = sharedPref.getString(APP_PREFERENCES_TOKEN, "")?:""
            }
            return token
        }

        fun setToken(token: String) {
            val editor = sharedPref.edit()
            editor.putString(APP_PREFERENCES_TOKEN, token)
            editor.apply()
        }

        fun deleteToken() {
            val editor = sharedPref.edit()
            editor.remove(APP_PREFERENCES_TOKEN)
            editor.apply()
        }
    }
    private lateinit var retrofit: Retrofit

    val db: MyDB by lazy {
        Room.databaseBuilder(
            this,
            MyDB::class.java,
            "my_database"
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

        sharedPref = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        retrofit = Retrofit.Builder()
            .baseUrl("https://fefu.t.feip.co/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        api = retrofit.create(Api::class.java)
    }
}