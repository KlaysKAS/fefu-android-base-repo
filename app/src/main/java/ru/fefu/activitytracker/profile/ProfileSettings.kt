package ru.fefu.activitytracker.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ru.fefu.activitytracker.R

class ProfileSettings : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val changePassword: TextView = view.findViewById(R.id.profile_change_password_btn)
        changePassword.setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .apply {
                    replace(
                        R.id.profile_flow_fragment,
                        ProfileChangePassword.newInstance()
                    )
                    addToBackStack(null)
                    commit()
                }
        }
    }

    companion object {
        fun newInstance() = ProfileSettings()
    }
}