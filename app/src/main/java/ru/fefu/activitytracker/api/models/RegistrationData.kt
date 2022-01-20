package ru.fefu.activitytracker.api.models

data class RegistrationData(
    val login: String,
    val password: String,
    val name: String,
    val gender: Int
)
