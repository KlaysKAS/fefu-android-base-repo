package ru.fefu.activitytracker.usersActivityPackage

import ru.fefu.activitytracker.activities.CardAbstract

class UsersActivityData(
    val distance: String,
    val username: String,
    val duration: String,
    val type: String,
    val comment: String? = null,
    val date: String
): CardAbstract()