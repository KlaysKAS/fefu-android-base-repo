package ru.fefu.activitytracker.newActivity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentManager
import ru.fefu.activitytracker.ParentFragmentManager
import ru.fefu.activitytracker.R

class NewActivityActivity : AppCompatActivity(), ParentFragmentManager {
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_activity)

        val cardView: CardView = findViewById(R.id.card_with_settings)
        cardView.setBackgroundResource(R.drawable.up_corner_25dp_shape)

        supportFragmentManager.beginTransaction().apply {
            add(
                R.id.new_activity_flow,
                NewActivityChooseFragment.newInstance(),
                "chooseActivity"
            )
            commit()
        }
    }

    override fun getActivitiesFragmentManager(): FragmentManager = supportFragmentManager
}
