package ru.fefu.activitytracker.myActivityPackage

import ru.fefu.activitytracker.activities.CardAbstract

data class MyActivityData(
    val distance: String,
    val duration: String,
    val type: String,
    val comment: String? = null,
    val date: String
): CardAbstract()