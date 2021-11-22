package ru.fefu.activitytracker.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import ru.fefu.activitytracker.R

class ProfileChangePassword : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_change_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar: Toolbar = view.findViewById(R.id.change_password_toolbar)
        val saveBtn: Button = view.findViewById(R.id.change_password_accept_btn)

        toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
        saveBtn.setOnClickListener { TODO("Not yet implements") }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileChangePassword()
    }
}