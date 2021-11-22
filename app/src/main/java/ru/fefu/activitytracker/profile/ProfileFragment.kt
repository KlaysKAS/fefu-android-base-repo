package ru.fefu.activitytracker.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.fefu.activitytracker.R

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        childFragmentManager.beginTransaction()
            .apply {
                add(
                    R.id.profile_flow_fragment,
                    ProfileSettings.newInstance(),
                    "profileSettings"
                )
                commit()
            }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}