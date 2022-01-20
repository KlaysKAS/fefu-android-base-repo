package ru.fefu.activitytracker.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.fefu.activitytracker.App
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.api.models.Profile
import ru.fefu.activitytracker.api.models.TokenAndProfile
import ru.fefu.activitytracker.welcomeRegLogin.WelcomeScreen

class ProfileSettings : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (App.profile == null) {
            getProfile()
        }
        setProfile()

        val exitButton = view.findViewById<Button>(R.id.exit_profile)
        exitButton.setOnClickListener { exit() }

        val changePassword: TextView = view.findViewById(R.id.profile_change_password_btn)
        changePassword.setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .apply {
                    replace(
                        R.id.profile_flow_fragment,
                        ProfileChangePassword.newInstance()
                    )
                    addToBackStack(null)
                    commit()
                }
        }
    }

    private fun setProfile() {
        val name = view?.findViewById<TextInputEditText>(R.id.profile_name_or_nick_text)
        val login = view?.findViewById<TextInputEditText>(R.id.profile_login_text)

        name?.setText(App.profile?.name)
        login?.setText(App.profile?.login)
    }

    private fun goToLogin() {
        val intent = Intent(activity?.baseContext, WelcomeScreen::class.java)
        startActivity(intent)
        activity?.finish()
    }

    private fun setError(error: String) {
        val errorView = view?.findViewById<TextView>(R.id.error_profile)
        errorView?.text = error
    }

    private fun exit() {
        App.getApi.logout(App.getToken()).enqueue(object: Callback<Unit> {
            override fun onResponse(
                call: Call<Unit>,
                response: Response<Unit>
            ) {
                when (response.code()) {
                    200 -> {
                        val body = response.body()
                        body?.let {
                            App.deleteToken()
                            goToLogin()
                        }
                    }
                    else -> {
                        setError("Что-то пошло не так: ${response.errorBody()}\nПопробуйте ещё!")
                    }
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                setError("Произошла ошибка, проверьте соединение и попробуйте снова.")
            }
        })
    }

    private fun getProfile() {
        App.getApi.getProfile(App.getToken()).enqueue(object: Callback<Profile> {
            override fun onResponse(call: Call<Profile>, response: Response<Profile>) {
                when (response.code()) {
                    200 -> {
                        val body = response.body()
                        body?.let {
                            App.profile = it
                            setProfile()
                        }

                    }
                    else -> {
                        App.deleteToken()
                        goToLogin()
                    }
                }
            }

            override fun onFailure(call: Call<Profile>, t: Throwable) {
                setError("Произошла ошибка, проверьте соединение и попробуйте снова.")
            }
        })
    }

    companion object {
        fun newInstance() = ProfileSettings()
    }
}