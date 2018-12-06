package com.example.tyfarris.reminderapp

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*

class CalendarFragment : Fragment() {

    private lateinit var model: MyModelView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = activity.run {
            ViewModelProviders.of(activity!!).get(MyModelView::class.java)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //set the info button to invisible
        activity?.toolbar_info_logo?.visibility = View.INVISIBLE

        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }
}