package ru.fefu.activitytracker.welcomeRegLogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.fefu.activitytracker.App
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.activities.NavigateActivity
import ru.fefu.activitytracker.api.models.LoginData
import ru.fefu.activitytracker.api.models.TokenAndProfile

class LoginScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)

        val loginButton = findViewById<Button>(R.id.login_button)
        loginButton.setOnClickListener { login() }

        backOnClick()
    }

    private fun backOnClick() {
        val backButton = findViewById<ImageView>(R.id.ArrowBack)
        backButton.setOnClickListener {
            finish()
        }
    }

    private fun login() {
        val loginInput = findViewById<TextInputEditText>(R.id.LoginInputText_login)
        val passwordInput = findViewById<TextInputEditText>(R.id.PasswordInputText_login)

        if (loginInput.text.toString() == "" || passwordInput.text.toString() == "") {
            setErrors("Все поля должны быть заполнены.")
            return
        }

        val loginData = LoginData(
            login = loginInput.text.toString(),
            password = passwordInput.text.toString()
        )

        App.getApi.login(loginData).enqueue(object: Callback<TokenAndProfile> {
            override fun onResponse(
                call: Call<TokenAndProfile>,
                response: Response<TokenAndProfile>
            ) {
                when (response.code()) {
                    200 -> {
                        val body = response.body()
                        body?.let {
                            App.profile = it.profile
                            App.setToken("Bearer ${it.token}")
                        }
                        goToMain()
                    }
                    422 -> {
                        setErrors("Данной связки логин-пароль не существует")
                    }
                    else -> {
                        setErrors(response.message())
                    }
                }
            }

            override fun onFailure(call: Call<TokenAndProfile>, t: Throwable) {
                setErrors("Произошла какая-то ошибка, попробуйте позднее.")
            }
        })
    }

    private fun setErrors(error: String) {
        val errorView = findViewById<TextView>(R.id.error_login)
        errorView.text = error
    }

    private fun goToMain() {
        val intent = Intent(this, NavigateActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }
}