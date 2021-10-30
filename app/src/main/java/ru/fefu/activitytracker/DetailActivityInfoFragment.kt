package ru.fefu.activitytracker

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import ru.fefu.activitytracker.MyActivityPackage.MyActivityAdapter

class DetailActivityInfoFragment(
    private val onItemListener: MyActivityAdapter.OnItemListener,
    private val userName: String
) :
    Fragment(), View.OnClickListener {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_activity_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val imgBtn = view.findViewById<ImageButton>(R.id.detailed_activity_my_arrowBack)
        imgBtn.setOnClickListener(this)

        val userName: TextView = view.findViewById(R.id.detailed_activity_users_userdate)
        userName.text = this.userName
    }

    override fun onClick(p0: View?) {
        onItemListener.onItemBack()
    }

    companion object {
        @JvmStatic
        fun newInstance(onItemListener: MyActivityAdapter.OnItemListener, userName: String = "") =
            DetailActivityInfoFragment(onItemListener, userName)
    }
}