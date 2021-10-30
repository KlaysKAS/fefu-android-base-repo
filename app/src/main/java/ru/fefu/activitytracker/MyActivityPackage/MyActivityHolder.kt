package ru.fefu.activitytracker.MyActivityPackage

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.fefu.activitytracker.CardAbstract
import ru.fefu.activitytracker.R

class MyActivityHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val ivDistance: TextView = itemView.findViewById(R.id.my_activity_card_distance)
    private val ivDuration: TextView = itemView.findViewById(R.id.my_activity_card_duration)
    private val ivType: TextView = itemView.findViewById(R.id.my_activity_card_type)
    private val ivDate: TextView = itemView.findViewById(R.id.my_activity_card_date)

    fun bind(item: CardAbstract) {
        item as MyActivityData
        ivDistance.text = item.distance
        ivDuration.text = item.duration
        ivType.text = item.type
        ivDate.text = item.date

    }
}