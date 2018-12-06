package com.example.tyfarris.reminderapp

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_reminders.*

class RemindersFragment : Fragment() {

    //private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var model: MyModelView

//    var myDataset = arrayOf("School List", "Thanksgiving List", "Random List", "Idea List")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //retrieve view model
        model = activity.run {
            ViewModelProviders.of(activity!!).get(MyModelView::class.java)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_reminders, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        val addButton = view.findViewById<FloatingActionButton>(R.id.buttonAddList)

        //set the info button to visible
        activity?.toolbar_info_logo?.visibility = View.INVISIBLE

        //recycler view
        viewManager = LinearLayoutManager(activity)
        viewAdapter = MyAdapter(this, model, model.lstDirectory)

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
            adapter = viewAdapter
        }

        //add list button
        addButton?.setOnClickListener{
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragment_container, AddListFragment())?.addToBackStack("addList")?.commit()
        }
        return view
    }
}