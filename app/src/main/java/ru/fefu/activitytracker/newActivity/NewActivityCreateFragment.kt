package ru.fefu.activitytracker.newActivity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import ru.fefu.activitytracker.App
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.db.Activities
import kotlin.random.Random

class NewActivityCreateFragment : Fragment() {
    private var type_id: Int = 0

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

        val finishButton: ImageButton = view.findViewById(R.id.new_activity_finish)
        finishButton.setOnClickListener {
            if (type_id > -1) {
                val millisEnd = System.currentTimeMillis() - Random.nextLong(0, 600000000)
                App.INSTANCE.db.activityDao().insert(
                    Activities(
                        id = 0,
                        userName = "",
                        type = type_id,
                        dateStart = millisEnd - Random.nextLong(600000, 60000000),
                        dateEnd = millisEnd,
                        latitude = 123.0,
                        longitude = 123.0
                    )
                )
            }
            parentFragmentManager.popBackStack()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(type_id: Int) = NewActivityCreateFragment().apply {
            arguments = Bundle().apply {
                putInt("type", type_id)
            }
        }
    }
}