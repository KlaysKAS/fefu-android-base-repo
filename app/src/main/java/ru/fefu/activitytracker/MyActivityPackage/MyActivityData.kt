package ru.fefu.activitytracker.MyActivityPackage

import ru.fefu.activitytracker.CardAbstract

data class MyActivityData(
    val distance: String,
    val duration: String,
    val type: String,
    val comment: String? = null,
    val date: String
): CardAbstract()