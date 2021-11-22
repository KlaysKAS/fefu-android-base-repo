package ru.fefu.activitytracker.detailActivity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.google.android.material.textfield.TextInputEditText
import ru.fefu.activitytracker.ParentFragmentManager
import ru.fefu.activitytracker.R

class DetailActivityInfoFragment : Fragment(R.layout.fragment_detail_activity_info) {
    private var username: String? = null
    private var isMyActivity: Boolean = true
    private var commentText: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString("username")
            isMyActivity = it.getBoolean("isMyActivity")
            commentText = it.getString("commentText")
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

        val textViewUserName =
            view.findViewById<TextView>(R.id.detailed_activity_users_username)
        val comment =
            view.findViewById<TextInputEditText>(R.id.detailed_activity_commentInput_edit)
        val toolbar =
            view.findViewById<Toolbar>(R.id.detailed_activity_toolbar)
        toolbar.setNavigationOnClickListener {
            val fragmentManager =
                (parentFragment as ParentFragmentManager).getActivitiesFragmentManager()
            fragmentManager.popBackStack()
        }
        comment.hint = commentText
        if (isMyActivity) {
            toolbar.inflateMenu(R.menu.toolbar_detailed_activity_menu)
            comment.isEnabled = true
        } else {
            textViewUserName.text = username
            comment.isEnabled = false
        }

    }

    companion object {
        @JvmStatic
        fun newInstance(
            username: String = "",
            commentText: String? = "Комментарий",
            isMyActivity: Boolean = true
        ) = DetailActivityInfoFragment().apply {
                arguments = Bundle().apply {
                    putString("username", username)
                    putString("commentText", commentText)
                    putBoolean("isMyActivity", isMyActivity)
                }
            }
    }
}