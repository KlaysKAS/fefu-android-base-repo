package ru.fefu.activitytracker.newActivity

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.fefu.activitytracker.R

class NewActivityAdapter(private val data: List<NewActivityData>) :
    RecyclerView.Adapter<NewActivityAdapter.NewActivityHolder>() {
    private var items = mutableListOf<View>()
    var selected = 0

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
            items[selected].isSelected = true
            itemView.setOnClickListener {
                var count = 0
                items.forEach { views ->
                    if (views == it) {
                        selected = count
                        views.isSelected = true
                    }
                    else
                        views.isSelected = false
                    count += 1
                }
            }
        }

        fun bind(item: NewActivityData) {
            ivType.text = item.type
        }
    }

}