package com.example.tyfarris.reminderapp

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*

class EditListNameFragment : Fragment() {
    private lateinit var model: MyModelView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = activity.run {
            ViewModelProviders.of(activity!!).get(MyModelView::class.java)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_add_list, container, false)
        val saveLstBtn = view.findViewById<Button>(R.id.buttonSaveList)
        val cancelBtn = view.findViewById<Button>(R.id.buttonCancelList)
        val editLst = view.findViewById<EditText>(R.id.editTextListName)
        lateinit var updatedLstName: String

        editLst.setText(model.lstDirectory[model.selectedListPosition].listName)

        //when list name is edited
        editLst.addTextChangedListener(object: TextWatcher {

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                updatedLstName = p0.toString()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        saveLstBtn?.setOnClickListener{
            model.lstDirectory[model.selectedListPosition].listName = updatedLstName

            activity?.title = updatedLstName

            val fm = activity?.supportFragmentManager
            fm?.popBackStack ("editListName", FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }

        cancelBtn?.setOnClickListener{
            val fm = activity?.supportFragmentManager
            fm?.popBackStack ("editListName", FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }

        return view
    }
}