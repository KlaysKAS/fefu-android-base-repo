package ru.fefu.activitytracker.detailActivity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.google.android.material.textfield.TextInputEditText
import ru.fefu.activitytracker.ActivitiesEnum
import ru.fefu.activitytracker.App
import ru.fefu.activitytracker.ParentFragmentManager
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.dateActivityPackage.DateActivityData
import ru.fefu.activitytracker.myActivityPackage.ActivityData
import ru.fefu.activitytracker.usersActivityPackage.UsersActivityData
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

private const val ARG_ACTIVITY_ID = "activity_id"

class DetailActivityInfoFragment : Fragment(R.layout.fragment_detail_activity_info) {
    private lateinit var toolbar: Toolbar
    private var activityId = 0
    private var mode = 0
    private lateinit var data: ActivityData

    private val usersData = listOf(
        UsersActivityData(
            distance = "14.32 км",
            duration = "2 часа 46 минут",
            type = "Сёрфинг",
            date = "14 часов назад",
            comment = "Я бежал очень сильно, ты так не сможешь",
            username = "@van_dorkholme"
        ),
        UsersActivityData(
            distance = "228 м",
            duration = "14 часов 48 минут",
            type = "Качели",
            date = "14 часов назад",
            username = "@tecnhiquepasha"
        ),
        UsersActivityData(
            distance = "1000 м",
            duration = "1 час 10 минут",
            type = "Езда на кадилак",
            date = "14 часов назад",
            username = "@morgen_shtern"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            activityId = it.getInt(ARG_ACTIVITY_ID)
            mode = it.getInt("mode")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_activity_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar = view.findViewById(R.id.detailed_activity_toolbar)
        toolbar.setNavigationOnClickListener {
            val fragmentManager =
                (parentFragment as ParentFragmentManager).getActivitiesFragmentManager()
            fragmentManager.popBackStack()
        }
        getAndBindActivity()
    }

    @SuppressLint("SetTextI18n")
    private fun bind() {
        val textViewUserName = view?.findViewById<TextView>(R.id.detailed_activity_users_username)
        val comment =
            view?.findViewById<TextInputEditText>(R.id.detailed_activity_commentInput_edit)
        val distanceView = view?.findViewById<TextView>(R.id.detailed_activity_distance)
        val durationView = view?.findViewById<TextView>(R.id.detailed_activity_duration)
        val dateStartView = view?.findViewById<TextView>(R.id.detailed_activity_startTime)
        val dateEndView = view?.findViewById<TextView>(R.id.detailed_activity_endTime)
        val dateView = view?.findViewById<TextView>(R.id.detailed_activity_date)

        val startTime = "%02d".format(data.date_start.hour) + ":" + "%02d".format(data.date_start.minute)
        val endTime = "%02d".format(data.date_end.hour) + ":" + "%02d".format(data.date_end.minute)

        toolbar.title = data.type
        distanceView?.text = data.distance
        dateStartView?.text = startTime
        dateEndView?.text = endTime
        durationView?.text = data.duration
        comment?.hint = data.comment
        if (data.user == App.username) {
            toolbar.inflateMenu(R.menu.toolbar_detailed_activity_menu)
            comment?.isEnabled = true
        } else {
            textViewUserName?.text = data.user
            comment?.isEnabled = false
        }

        if (LocalDateTime.now().equals(data.date_end)) {
            dateView?.text =
                Duration.between(data.date_end, LocalDateTime.now()).toHours().toString() + "ч. назад"
        } else {
            dateView?.text =
                "${data.date_end.dayOfMonth}.${data.date_end.monthValue}.${data.date_end.year}"
        }
    }

    private fun getAndBindActivity() {
        if (mode == 0) {
            val activities = App.INSTANCE.db.activityDao().getActivityById(activityId)
            data = ActivityData(
                id = activities.id,
                distance = "4 км",
                duration = "1 ч",
                type = ActivitiesEnum.values()[activities.type].type,
                date_start = Instant
                    .ofEpochMilli(activities.dateStart)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime(),
                date_end = Instant
                    .ofEpochMilli(activities.dateEnd)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime(),
            )
        }
        else {
            data = ActivityData(
                user = usersData[activityId].username,
                distance = usersData[activityId].distance,
                duration = usersData[activityId].duration,
                type = usersData[activityId].type,
                comment = usersData[activityId].comment,
                date_start = LocalDateTime.now().minusHours(20),
                date_end = LocalDateTime.now().minusHours(14)
            )
        }
        bind()
    }

    companion object {
        @JvmStatic
        fun newInstance(id: Int, mode: Int) = DetailActivityInfoFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_ACTIVITY_ID, id)
                putInt("mode", mode)
            }
        }
    }
}