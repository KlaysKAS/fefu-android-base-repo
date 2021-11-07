package ru.fefu.activitytracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import ru.fefu.activitytracker.DetailActivity.ActivitiesFragmentManager

class ActivityFlowFragment : Fragment(R.layout.fragment_activity_flow), ActivitiesFragmentManager {
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
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ActivityFlowFragment()
    }

    override fun getActivitiesFragmentManager(): FragmentManager = childFragmentManager
}