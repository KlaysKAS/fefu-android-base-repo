package ru.fefu.activitytracker.newActivity

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import ru.fefu.activitytracker.ActivitiesEnum
import ru.fefu.activitytracker.App
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.db.entity.Activities
import ru.fefu.activitytracker.gps.CoordToDistance
import kotlin.math.roundToInt

class NewActivityCreateFragment : Fragment() {
    private var typeId: Int = 0
    private var mode: Int = 0
    private var activityId: Long = -1
    private var lastChecked = -1

    private lateinit var distanceView: TextView
    private lateinit var typeView: TextView

    private var distance: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            typeId = it.getInt("type")
            mode = it.getInt("mode")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_activity_create, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        distanceView = view.findViewById(R.id.new_activity_create_distance)
        typeView = view.findViewById(R.id.new_activity_create_type)

        if (mode == 0) {
            if (typeId > -1) {
                App.INSTANCE.db.activityDao().insert(
                    Activities(
                        id = 0,
                        userName = "",
                        type = typeId,
                        dateStart = System.currentTimeMillis()
                    )
                )
            }
        }
        getCurActivity()

        val finishButton: ImageButton = view.findViewById(R.id.new_activity_finish)
        finishButton.setOnClickListener {
            requireActivity().finish()
            parentFragmentManager.popBackStack()
            val intent = Intent(activity, NewActivityService::class.java).apply {
                action = NewActivityService.ACTION_CANCEL
            }
            requireActivity().startService(intent)
        }
    }


    private fun setDistance(dist: Double) {
        if (!dist.isNaN()) {
            val s =
                if (dist < 1000) {
                    "${dist.roundToInt()} м"
                } else {
                    val d: Double = dist / 1000.0
                    "${d.format(2)} км"
                }
            distanceView.text = s
        }
    }

    private fun getCurActivity() {
        activityId = App.INSTANCE.db.activityDao().getLastActivity() ?: -1
        if (activityId > -1) {
            typeView.text = ActivitiesEnum
                .values()[App.INSTANCE.db.activityDao().getActivityById(activityId).activity.type]
                .type
            App.INSTANCE.db.activityDao().getCoords(activityId).observe(viewLifecycleOwner) {
                if (lastChecked > -1) {
                    for (i in lastChecked until it.size)
                        if (it.size > lastChecked + 1) {
                            distance += CoordToDistance.getDistanceFromLatLonInM(it[lastChecked], it[lastChecked + 1])
                            lastChecked += 1
                        }
                }
                else {
                    lastChecked = 0
                }
                setDistance(distance)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(type_id: Int, mode: Int = 0) = NewActivityCreateFragment().apply {
            arguments = Bundle().apply {
                putInt("type", type_id)
                putInt("mode", mode)
            }
        }
    }

    private fun Double.format(digits: Int) = "%.${digits}f".format(this)
}