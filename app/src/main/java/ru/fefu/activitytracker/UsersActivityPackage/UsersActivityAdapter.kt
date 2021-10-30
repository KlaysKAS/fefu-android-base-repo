package ru.fefu.activitytracker.UsersActivityPackage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.fefu.activitytracker.CardAbstract
import ru.fefu.activitytracker.DateActivityPackage.DateActivityHolder
import ru.fefu.activitytracker.MyActivityPackage.MyActivityAdapter
import ru.fefu.activitytracker.R

class UsersActivityAdapter(
    private val data: List<CardAbstract>,
    private val onItemListener: MyActivityAdapter.OnItemListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_TYPE_ACTIVITY -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.users_activity_card, parent, false)
                UsersActivityHolder(view)
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
                (holder as UsersActivityHolder).bind(data[position])
                holder.itemView.setOnClickListener(this)
            }
            else -> (holder as DateActivityHolder).bind(data[position])

        }
    }

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int {
        return when (data[position]) {
            is UsersActivityData -> ITEM_TYPE_ACTIVITY
            else -> ITEM_TYPE_DATE
        }
    }

    companion object {
        private const val ITEM_TYPE_ACTIVITY = 0
        private const val ITEM_TYPE_DATE = 1
    }

    override fun onClick(p0: View?) {
        onItemListener.onItemClick(p0!!.findViewById<TextView>(R.id.users_activity_card_username).text.toString())
    }
}