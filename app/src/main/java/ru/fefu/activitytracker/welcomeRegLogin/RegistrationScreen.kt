package ru.fefu.activitytracker.welcomeRegLogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.fefu.activitytracker.App
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.activities.NavigateActivity
import ru.fefu.activitytracker.api.models.RegistrationData
import ru.fefu.activitytracker.api.models.TokenAndProfile
import java.lang.StringBuilder

class RegistrationScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_screen)

        val registrationButton = findViewById<Button>(R.id.registration_button)
        registrationButton.setOnClickListener { registration() }

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

    private fun registration() {
        val loginInput = findViewById<TextInputEditText>(R.id.LoginInputText)
        val passwordInput = findViewById<TextInputEditText>(R.id.PasswordInputText)
        val repeatPasswordInput = findViewById<TextInputEditText>(R.id.RepeatPasswordInputText)
        val nameInput = findViewById<TextInputEditText>(R.id.NameInputText)
        val genderInput = findViewById<AutoCompleteTextView>(R.id.GenderDropDown)

        val gender = when (genderInput.text.toString()) {
            "Male" -> 0
            "Female" -> 1
            else -> -1
        }
        val passNotError = passwordInput.text.toString() == repeatPasswordInput.text.toString()

        val errors = mutableListOf<String>()
        if (gender == -1) errors.add("Проверьте выбранный пол, допускаются Male, Female.")
        if (!passNotError) errors.add("Введённые пароли не совпадают.")
        if (loginInput.text == null || passwordInput.text == null || nameInput.text == null)
            errors.add("Все поля должны быть заполнены.")
         if (errors.size > 0) {
            setErrors(errors)
            return
        }

        val reg = RegistrationData(
            login = loginInput.text.toString(),
            password = passwordInput.text.toString(),
            name = nameInput.text.toString(),
            gender = gender
        )

        App.getApi.registration(reg).enqueue(object: Callback<TokenAndProfile> {
            override fun onResponse(call: Call<TokenAndProfile>, response: Response<TokenAndProfile>) {
                when (response.code()) {
                    201 -> {
                        val body = response.body()
                        body?.let {
                            App.profile = it.profile
                            App.setToken("Bearer ${it.token}")
                        }
                        goToMain()
                    }
                    422 -> {
                        setErrors(mutableListOf("Проверьте данные и попробуйте ещё раз"))
                    }
                    else -> {
                        setErrors(mutableListOf(response.message()))
                    }
                }
            }

            override fun onFailure(call: Call<TokenAndProfile>, t: Throwable) {
                setErrors(mutableListOf("Произошла какая-то ошибка, попробуйте позднее."))
            }
        })
    }

    private fun setErrors(errors: MutableList<String>) {
        val errorsView = findViewById<TextView>(R.id.errors_registration)
        val text = StringBuilder()
        errors.forEachIndexed { index, string ->
            if (index != 0)
                text.append("\n")
            text.append(string)
        }
        errorsView.text = text.toString()
    }

    private fun goToMain() {
        val intent = Intent(this, NavigateActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }
}