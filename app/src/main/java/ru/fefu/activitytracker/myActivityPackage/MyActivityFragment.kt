package ru.fefu.activitytracker.myActivityPackage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.fefu.activitytracker.dateActivityPackage.DateActivityData
import ru.fefu.activitytracker.ParentFragmentManager
import ru.fefu.activitytracker.detailActivity.DetailActivityInfoFragment
import ru.fefu.activitytracker.R

class MyActivityFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private val dataList = listOf(
        DateActivityData("Вчера"),
        MyActivityData(
            distance = "14.32 км",
            duration = "2 часа 46 минут",
            type = "Сёрфинг",
            date = "14 часов назад"
        ),
        DateActivityData("Май 2022 года"),
        MyActivityData(
            distance = "1000 м",
            duration = "60 минут",
            type = "Велосипед",
            date = "29.05.2022"
        ),
        MyActivityData(
            distance = "1000 м",
            duration = "60 минут",
            type = "Велосипед",
            date = "29.05.2022"
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_activity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.my_activity_recycler)
        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = MyActivityAdapter(
            dataList
        )
        recyclerView.adapter = adapter
        adapter.setItemClickListener {
            (parentFragment as ParentFragmentManager).getActivitiesFragmentManager()
                .beginTransaction()
                .apply {
                    replace(
                        R.id.activity_flow_container,
                        DetailActivityInfoFragment.newInstance(),
                        "detailedActivity"
                    )
                    addToBackStack(null)
                    commit()
                }
        }
    }

    companion object {
        fun newInstance() = MyActivityFragment()
    }


}