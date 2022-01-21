package ru.fefu.activitytracker.myActivityPackage

import ru.fefu.activitytracker.activities.CardAbstract
import java.time.LocalDateTime

data class ActivityData(
    val id: Int = 0,
    val user: String = "",
    val distance: String,
    val duration: String,
    val type: String,
    val comment: String? = null,
    val date_start: LocalDateTime,
    val date_end: LocalDateTime
): CardAbstract()