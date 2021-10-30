package ru.fefu.activitytracker.UsersActivityPackage

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.fefu.activitytracker.CardAbstract
import ru.fefu.activitytracker.R

class UsersActivityHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val ivDistance: TextView = itemView.findViewById(R.id.users_activity_card_distance)
    private val ivUsername: TextView = itemView.findViewById(R.id.users_activity_card_username)
    private val ivDuration: TextView = itemView.findViewById(R.id.users_activity_card_duration)
    private val ivType: TextView = itemView.findViewById(R.id.users_activity_card_type)
    private val ivDate: TextView = itemView.findViewById(R.id.users_activity_card_date)

    fun bind(item: CardAbstract) {
        item as UsersActivityData
        ivDistance.text = item.distance
        ivUsername.text = item.username
        ivDuration.text = item.duration
        ivType.text = item.type
        ivDate.text = item.date
    }
}