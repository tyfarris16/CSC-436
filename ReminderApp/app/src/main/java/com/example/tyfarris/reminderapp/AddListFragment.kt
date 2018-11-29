package com.example.tyfarris.reminderapp

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.fragment_add_list.*

class AddListFragment : Fragment() {
    private lateinit var model: MyModelView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_add_list, container, false)
        val saveLstBtn = view.findViewById<Button>(R.id.buttonSaveList)
        val cancelBtn = view.findViewById<Button>(R.id.buttonCancelList)
        val editLst = view.findViewById<EditText>(R.id.editTextListName)

        model = activity.run {
            ViewModelProviders.of(activity!!).get(MyModelView::class.java)
        }

        saveLstBtn?.setOnClickListener{
            //add list to lstDirectory in view model
            model.addReminderList(editLst.text.toString())

            val fm = activity?.supportFragmentManager
            fm?.popBackStack ("addList", FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }

        cancelBtn?.setOnClickListener{
            val fm = activity?.supportFragmentManager
            fm?.popBackStack ("addList", FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }

        return view
    }
}