package ru.fefu.activitytracker.UsersActivityPackage

import ru.fefu.activitytracker.CardAbstract

class UsersActivityData(
    val distance: String,
    val username: String,
    val duration: String,
    val type: String,
    val date: String
): CardAbstract()