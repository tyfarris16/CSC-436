package com.example.tyfarris.reminderapp

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_reminders.*

class EditListFragment : Fragment() {

    private lateinit var viewAdapter2: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var model: MyModelView
    var listPosition : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = arguments
        var reminderRef = bundle?.get("SelectedReminderList") as MyModelView.ReminderList
        listPosition = bundle?.getInt("SelectedReminderListPosition")

        model = activity.run {
            ViewModelProviders.of(activity!!).get(MyModelView::class.java)
        }

        //change the title of the bar
        activity?.title = reminderRef.listName
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_reminders, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        val addButton = view.findViewById<FloatingActionButton>(R.id.buttonAddList)

        //recycler view
        viewManager = LinearLayoutManager(activity)
        viewAdapter2 = MyAdapter2(this, model, listPosition!!, model.lstDirectory[listPosition!!])

        //notify that list directory has been updated
        recycler_view?.adapter?.notifyDataSetChanged()

        // = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter2
        }

        //add list button
        addButton?.setOnClickListener{
            //Toast.makeText(context, "HELLO", Toast.LENGTH_SHORT).show()
            //activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragment_container, AddListFragment())?.addToBackStack("addList")?.commit()
        //TODO: Get this working
        }

        return view
    }

}