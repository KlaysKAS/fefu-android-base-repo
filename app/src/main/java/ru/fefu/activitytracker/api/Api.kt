package ru.fefu.activitytracker.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import ru.fefu.activitytracker.api.models.LoginData
import ru.fefu.activitytracker.api.models.Profile
import ru.fefu.activitytracker.api.models.RegistrationData
import ru.fefu.activitytracker.api.models.TokenAndProfile

interface Api {
    @POST("api/auth/register")
    fun registration(@Body data: RegistrationData): Call<TokenAndProfile>

    @POST("api/auth/login")
    fun login(@Body data: LoginData): Call<TokenAndProfile>

    @POST("api/auth/logout")
    fun logout(@Header("Authorization") token: String): Call<Unit>

    @GET("api/user/profile")
    fun getProfile(@Header("Authorization") token: String): Call<Profile>
}