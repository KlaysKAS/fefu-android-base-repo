package ru.fefu.activitytracker.newActivity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import ru.fefu.activitytracker.App
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.db.entity.Activities
import ru.fefu.activitytracker.gps.CoordToDistance
import kotlin.math.roundToInt

class NewActivityCreateFragment : Fragment() {
    private var type_id: Int = 0
    private var activityId: Int = -1
    private var lastChecked = -1

    private lateinit var distanceView: TextView

    private var distance: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            type_id = it.getInt("type")
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

        if (type_id > -1) {
            App.INSTANCE.db.activityDao().insert(
                Activities(
                    id = 0,
                    userName = "",
                    type = type_id,
                    dateStart = System.currentTimeMillis()
                )
            )
        }
        getCurActivity()
        NewActivityService.startForeground(requireContext(), activityId)


        val finishButton: ImageButton = view.findViewById(R.id.new_activity_finish)
        finishButton.setOnClickListener {
            finishActivity()
            parentFragmentManager.popBackStack()
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
        activityId = App.INSTANCE.db.activityDao().getLastActivity()?.id ?: -1
        if (activityId > -1) {
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

    private fun finishActivity() {
        App.INSTANCE.db.activityDao().finishActivity(System.currentTimeMillis(), activityId)
    }

    companion object {
        @JvmStatic
        fun newInstance(type_id: Int) = NewActivityCreateFragment().apply {
            arguments = Bundle().apply {
                putInt("type", type_id)
            }
        }
    }

    fun Double.format(digits: Int) = "%.${digits}f".format(this)
}