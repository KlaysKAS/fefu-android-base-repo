package ru.fefu.activitytracker.usersActivityPackage

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

class UsersActivityFragment  : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private val dataList = listOf(
        DateActivityData("Вчера"),
        UsersActivityData(
            distance = "14.32 км",
            duration = "2 часа 46 минут",
            type = "Сёрфинг",
            date = "14 часов назад",
            comment = "Я бежал очень сильно, ты так не сможешь",
            username = "@van_dorkholme"
        ),
        UsersActivityData(
            distance = "228 м",
            duration = "14 часов 48 минут",
            type = "Качели",
            date = "14 часов назад",
            username = "@tecnhiquepasha"
        ),
        UsersActivityData(
            distance = "1000 м",
            duration = "1 час 10 минут",
            type = "Езда на кадилак",
            date = "14 часов назад",
            username = "@morgen_shtern"
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_users_activity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.users_activity_recycler)
        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = UsersActivityAdapter(
            dataList
        )
        recyclerView.adapter = adapter
        adapter.setItemClickListener {
            (parentFragment as ParentFragmentManager).getActivitiesFragmentManager()
                .beginTransaction()
                .apply {
                    replace(
                        R.id.activity_flow_container,
                        DetailActivityInfoFragment.newInstance(
                            username = (dataList[it] as UsersActivityData).username,
                            commentText = (dataList[it] as UsersActivityData).comment,
                            isMyActivity = false
                        )
                    )
                    addToBackStack(null)
                    commit()
                }
        }
    }

    companion object {
        fun newInstance() = UsersActivityFragment()
    }
}