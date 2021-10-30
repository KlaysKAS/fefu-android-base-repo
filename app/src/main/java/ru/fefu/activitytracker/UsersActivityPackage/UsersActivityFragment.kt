package ru.fefu.activitytracker.UsersActivityPackage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.fefu.activitytracker.DateActivityPackage.DateActivityData
import ru.fefu.activitytracker.MyActivityPackage.MyActivityAdapter
import ru.fefu.activitytracker.R

class UsersActivityFragment(private val onItemListener: MyActivityAdapter.OnItemListener)  : Fragment() {

    private lateinit var recyclerView: RecyclerView

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
        recyclerView.adapter = UsersActivityAdapter(
            listOf(
                DateActivityData("Вчера"),
                UsersActivityData(
                    distance = "14.32 км",
                    duration = "2 часа 46 минут",
                    type = "Сёрфинг",
                    date = "14 часов назад",
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
            ),
            onItemListener
        )
    }

    companion object {
        fun newInstance(onItemListener: MyActivityAdapter.OnItemListener) = UsersActivityFragment(onItemListener)
    }
}