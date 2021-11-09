package ru.fefu.activitytracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import ru.fefu.activitytracker.newActivity.NewActivityAdapter
import ru.fefu.activitytracker.newActivity.NewActivityData

class NewActivityChooseFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private val dataList = listOf(
        NewActivityData("Велосипед"),
        NewActivityData("Бег"),
        NewActivityData("Езда на кадилак"),
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_activity_choose, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.new_activity_recycler_view)
        val adapter = NewActivityAdapter(dataList)
        recyclerView.adapter = adapter

        val btn: Button = view.findViewById(R.id.new_activity_btn_create)
        btn.setOnClickListener {
            parentFragmentManager
                .beginTransaction().apply {
                    replace(
                        R.id.new_activity_flow,
                        NewActivityCreateFragment.newInstance(),
                        "createActivity"
                    )
                    addToBackStack(null)
                    commit()
                }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = NewActivityChooseFragment()
    }
}