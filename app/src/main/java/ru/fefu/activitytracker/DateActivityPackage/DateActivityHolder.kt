package ru.fefu.activitytracker.DateActivityPackage

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.fefu.activitytracker.CardAbstract
import ru.fefu.activitytracker.R

class DateActivityHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val ivDate: TextView = itemView.findViewById(R.id.date_activity_card_date)

    fun bind(item: CardAbstract) {
        item as DateActivityData
        ivDate.text = item.date
    }
}