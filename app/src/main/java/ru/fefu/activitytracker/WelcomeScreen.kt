package ru.fefu.activitytracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class WelcomeScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_screen)

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