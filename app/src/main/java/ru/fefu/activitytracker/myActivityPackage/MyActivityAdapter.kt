package ru.fefu.activitytracker.myActivityPackage

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.fefu.activitytracker.activities.CardAbstract
import ru.fefu.activitytracker.dateActivityPackage.DateActivityHolder
import ru.fefu.activitytracker.R
import java.time.LocalDateTime

class MyActivityAdapter(private val data: List<CardAbstract>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var ItemClickListener: (Int) -> Unit = {}

    fun setItemClickListener(listener: (Int) -> Unit) {
        ItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_TYPE_ACTIVITY -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.my_activity_card, parent, false)
                MyActivityHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.activity_date_card, parent, false)
                DateActivityHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ITEM_TYPE_ACTIVITY -> {
                (holder as MyActivityHolder).bind(data[position])
            }
            else -> {
                (holder as DateActivityHolder).bind(data[position])
            }

        }
    }

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int {
        return when (data[position]) {
            is ActivityData -> ITEM_TYPE_ACTIVITY
            else -> ITEM_TYPE_DATE
        }
    }

    companion object {
        private const val ITEM_TYPE_ACTIVITY = 0
        private const val ITEM_TYPE_DATE = 1
    }

    inner class MyActivityHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivDistance: TextView = itemView.findViewById(R.id.my_activity_card_distance)
        private val ivDuration: TextView = itemView.findViewById(R.id.my_activity_card_duration)
        private val ivType: TextView = itemView.findViewById(R.id.my_activity_card_type)
        private val ivDate: TextView = itemView.findViewById(R.id.my_activity_card_date)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION)
                    ItemClickListener(position)
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(item: CardAbstract) {
            item as ActivityData
            ivDistance.text = item.distance
            ivDuration.text = item.duration
            ivType.text = item.type
            ivDate.text =
                "%02d".format(item.date_end.dayOfMonth) +
                "." +
                "%02d".format(item.date_start.monthValue)
        }
    }

}