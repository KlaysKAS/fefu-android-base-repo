package ru.fefu.activitytracker.api.models

data class Profile(
    val id: Int,
    val name: String,
    val login: String,
    val gender: Gender
)

data class Gender(
    val code: Int,
    val name: String
)