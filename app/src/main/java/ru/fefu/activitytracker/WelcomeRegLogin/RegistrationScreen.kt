package ru.fefu.activitytracker.WelcomeRegLogin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import ru.fefu.activitytracker.R

class RegistrationScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_screen)

        backOnClick()
        createDropDownMenu()
    }

    private fun createDropDownMenu() {
        val genderCompleteTextView = findViewById<AutoCompleteTextView>(R.id.GenderDropDown)
        val genderList = arrayListOf("Male", "Female")
        val genderAdapter = ArrayAdapter(
            applicationContext,
            R.layout.support_simple_spinner_dropdown_item,
            genderList
        )
        genderCompleteTextView.setAdapter(genderAdapter)
        genderCompleteTextView.threshold = 0
    }

    private fun backOnClick() {
        val backButton = findViewById<ImageView>(R.id.ArrowBack)
        backButton.setOnClickListener {
            finish()
        }
    }
}