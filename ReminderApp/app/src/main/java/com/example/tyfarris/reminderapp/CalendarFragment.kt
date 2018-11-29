package com.example.tyfarris.reminderapp

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class CalendarFragment : Fragment() {

    private lateinit var model: MyModelView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        model = activity.run {
            ViewModelProviders.of(activity!!).get(MyModelView::class.java)
        }
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }
}