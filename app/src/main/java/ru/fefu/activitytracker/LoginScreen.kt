package ru.fefu.activitytracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class LoginScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)

        backOnClick()
    }

    private fun backOnClick() {
        val backButton = findViewById<ImageView>(R.id.ArrowBack)
        backButton.setOnClickListener {
            finish()
        }
    }
}