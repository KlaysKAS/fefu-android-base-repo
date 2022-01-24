package ru.fefu.activitytracker.welcomeRegLogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import ru.fefu.activitytracker.App
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.activities.NavigateActivity

class WelcomeScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_screen)

        if (App.getToken() != "") {
            val intent = Intent(this, NavigateActivity::class.java)
            startActivity(intent)
            finish()
        }

        registrationOnClick()
        loginOnClick()
    }

    private fun registrationOnClick() {
        val registrationButton = findViewById<Button>(R.id.Registration)
        registrationButton.setOnClickListener {
            val intent = Intent(this, RegistrationScreen::class.java)
            startActivity(intent)
        }
    }

    private fun loginOnClick() {
        val alreadyRegistrationButton = findViewById<TextView>(R.id.AlreadyRegistered)
        alreadyRegistrationButton.setOnClickListener {
            val intent = Intent(this, LoginScreen::class.java)
            startActivity(intent)
        }
    }


}