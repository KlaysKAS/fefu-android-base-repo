package ru.fefu.activitytracker.activities

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentManager
import ru.fefu.activitytracker.newActivity.NewActivityActivity
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.ParentFragmentManager

class ActivityFlowFragment : Fragment(R.layout.fragment_activity_flow), ParentFragmentManager {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_activity_flow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            childFragmentManager.beginTransaction().apply {
                add(
                    R.id.activity_flow_container,
                    CollectionAdapterFragment.newInstance(),
                    "activities"
                )
                commit()
            }

            val btnToStartNewActivity: ImageView = view.findViewById(R.id.btn_to_start_new_activity)
            btnToStartNewActivity.setOnClickListener {
                val intent = Intent(it.context, NewActivityActivity::class.java)
                startActivity(intent)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ActivityFlowFragment()
    }

    override fun getActivitiesFragmentManager(): FragmentManager = childFragmentManager
}