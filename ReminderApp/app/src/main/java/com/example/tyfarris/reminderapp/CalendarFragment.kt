package com.example.tyfarris.reminderapp

import android.app.usage.UsageEvents
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.util.EventLog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class CalendarFragment : Fragment() {

    private lateinit var model: MyModelView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = activity.run {
            ViewModelProviders.of(activity!!).get(MyModelView::class.java)
        }

        //change the title of the bar
        activity?.title = "Calendar"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_calendar, container, false)
        val calendar = Calendar.getInstance()

        //set the info button to invisible
        activity?.toolbar_info_logo?.visibility = View.INVISIBLE

        //set an event in calendar
        return view
    }
}