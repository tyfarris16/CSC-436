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
import android.widget.Toast

class EditReminderFragment : Fragment() {
    private lateinit var model: MyModelView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model = activity.run {
            ViewModelProviders.of(activity!!).get(MyModelView::class.java)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.details, container, false)
        //buttons
        val saveBtn = view.findViewById<Button>(R.id.buttonSaveReminder)
        val cancelBtn = view.findViewById<Button>(R.id.buttonCancelReminder)

        //edit texts
        val event = view.findViewById<EditText>(R.id.editEvent)
        val time = view.findViewById<EditText>(R.id.editTime)
        val date= view.findViewById<EditText>(R.id.editDate)
        val place= view.findViewById<EditText>(R.id.editPlace)
        val description = view.findViewById<EditText>(R.id.editDescription)

        //updated values
        var updatedEvent = ""
        var updatedTime = ""
        var updatedDate = ""
        var updatedPlace = ""
        var updatedDescription = ""

        //booleans to keep track of which is updated
        var hasEventChanged = false
        var hasTimeChanged = false
        var hasDateChanged = false
        var hasPlaceChanged = false
        var hasDescriptionChanged = false

        //references to edit texts on fragment
        event.setText(model.lstDirectory[model.selectedListPosition].reminderList[model.selectedReminderPos].event)
        time.setText(model.lstDirectory[model.selectedListPosition].reminderList[model.selectedReminderPos].time)
        date.setText(model.lstDirectory[model.selectedListPosition].reminderList[model.selectedReminderPos].date)
        place.setText(model.lstDirectory[model.selectedListPosition].reminderList[model.selectedReminderPos].place)
        description.setText(model.lstDirectory[model.selectedListPosition].reminderList[model.selectedReminderPos].description)

        //when event is edited
        event.addTextChangedListener(object: TextWatcher {

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                updatedEvent = p0.toString()
                hasEventChanged = true
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        //when time is edited
        time.addTextChangedListener(object: TextWatcher {

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                updatedTime = p0.toString()
                hasTimeChanged = true
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        //when date is edited
        date.addTextChangedListener(object: TextWatcher {

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                updatedDate = p0.toString()
                hasDateChanged = true
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        //when place is edited
        place.addTextChangedListener(object: TextWatcher {

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                updatedPlace = p0.toString()
                hasPlaceChanged = true
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        //when description is edited
        description.addTextChangedListener(object: TextWatcher {

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                updatedDescription = p0.toString()
                hasDescriptionChanged = true
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        saveBtn?.setOnClickListener{
            //assign the new edit text values
            if (hasEventChanged) {
                model.lstDirectory[model.selectedListPosition].reminderList[model.selectedReminderPos].event = updatedEvent
            }

            if (hasTimeChanged) {
                model.lstDirectory[model.selectedListPosition].reminderList[model.selectedReminderPos].time = updatedTime
            }

            if (hasDateChanged) {
                model.lstDirectory[model.selectedListPosition].reminderList[model.selectedReminderPos].date = updatedDate
            }

            if (hasPlaceChanged) {
                model.lstDirectory[model.selectedListPosition].reminderList[model.selectedReminderPos].place = updatedPlace
            }

            if (hasDescriptionChanged) {
                model.lstDirectory[model.selectedListPosition].reminderList[model.selectedReminderPos].description = updatedDescription
            }

            val fm = activity?.supportFragmentManager
            fm?.popBackStack ("editReminder", FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }

        cancelBtn?.setOnClickListener{
            val fm = activity?.supportFragmentManager
            fm?.popBackStack ("editReminder", FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }

        return view
    }
}