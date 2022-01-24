package ru.fefu.activitytracker.myActivityPackage

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.fefu.activitytracker.ActivitiesEnum
import ru.fefu.activitytracker.App
import ru.fefu.activitytracker.dateActivityPackage.DateActivityData
import ru.fefu.activitytracker.ParentFragmentManager
import ru.fefu.activitytracker.detailActivity.DetailActivityInfoFragment
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.activities.CardAbstract
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class MyActivityFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private val activities = mutableListOf<ActivityData>()
    private val dataList = mutableListOf<CardAbstract>()
    private var activityAdapter = MyActivityAdapter(dataList)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_activity, container, false)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        App.INSTANCE.db.activityDao().getAll().observe(viewLifecycleOwner) {
            activities.clear()
            dataList.clear()
            for (activity in it) {
                val startDate = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(activity.dateStart),
                    ZoneId.systemDefault()
                )
                val endDate = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(activity.dateEnd),
                    ZoneId.systemDefault()
                )
                val type = ActivitiesEnum.values()[activity.type].type
                val distance = "5 км"
                activities.add(
                    ActivityData(
                        id = activity.id,
                        distance = distance,
                        type = type,
                        date_start = startDate,
                        date_end = endDate,
                        duration = "12 ч"
                    )
                )
            }
            createDate(activities)
            activityAdapter.notifyDataSetChanged()
        }

        recyclerView = view.findViewById(R.id.my_activity_recycler)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = activityAdapter
        activityAdapter.setItemClickListener {
            if (it in dataList.indices) {
                val pos = it
                (parentFragment as ParentFragmentManager).getActivitiesFragmentManager()
                    .beginTransaction()
                    .apply {
                        replace(
                            R.id.activity_flow_container,
                            DetailActivityInfoFragment.newInstance((dataList[pos] as ActivityData).id, 0),
                            "detailedActivity"
                        )
                        addToBackStack(null)
                        commit()
                    }
            }
        }
    }

    fun createDate(activities: List<ActivityData>) {
        val currentDate = LocalDateTime.now()
        var lastDate = DateActivityData("0")
        activities.forEach {
            if (currentDate.year == it.date_end.year &&
                currentDate.month == it.date_end.month &&
                currentDate.dayOfMonth == it.date_end.dayOfMonth
            ) {
                if (lastDate.date != "Сегодня")
                    lastDate = DateActivityData("Сегодня")
                dataList.add(lastDate)
            } else {
                if (lastDate.date != "${month.get(it.date_end.monthValue)} ${it.date_end.year} года") {
                    lastDate = DateActivityData(
                        "${month[it.date_end.monthValue]} ${it.date_end.year} года"
                    )
                    dataList.add(lastDate)
                }
            }
            dataList.add(it)
        }
    }

    companion object {
        fun newInstance() = MyActivityFragment()

        val month = mapOf(
            1 to "Январь",
            2 to "Февраль",
            3 to "Март",
            4 to "Апрель",
            5 to "Май",
            6 to "Июнь",
            7 to "Июль",
            8 to "Август",
            9 to "Сентябрь",
            10 to "Октябрь",
            11 to "Ноябрь",
            12 to "Декабрь"
        )
    }
}