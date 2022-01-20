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
import ru.fefu.activitytracker.App
import ru.fefu.activitytracker.ParentFragmentManager
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.myActivityPackage.ActivityData
import java.time.Duration
import java.time.LocalDateTime

class DetailActivityInfoFragment(private val data: ActivityData) : Fragment(R.layout.fragment_detail_activity_info) {
    private lateinit var toolbar: Toolbar

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
        bind()
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
        if (data.user == "") {
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


    companion object {
        @JvmStatic
        fun newInstance(data: ActivityData) = DetailActivityInfoFragment(data)
    }
}