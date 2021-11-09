package ru.fefu.activitytracker.newActivity

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import ru.fefu.activitytracker.R

class NewActivityAdapter(private val data: List<NewActivityData>) :
    RecyclerView.Adapter<NewActivityAdapter.NewActivityHolder>() {
    private var items = mutableListOf<View>()

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewActivityAdapter.NewActivityHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.new_activity_card, parent, false)
        items.add(view)
        return NewActivityHolder(view)
    }

    override fun onBindViewHolder(holder: NewActivityAdapter.NewActivityHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    @SuppressLint("UseCompatLoadingForDrawables")
    inner class NewActivityHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivType: TextView = itemView.findViewById(R.id.new_activity_type)

        init {
            itemView.setOnClickListener {
                items.forEach { views ->
                    views.isSelected = it == views
                }
            }
        }

        fun bind(item: NewActivityData) {
            ivType.text = item.type
        }
    }

}