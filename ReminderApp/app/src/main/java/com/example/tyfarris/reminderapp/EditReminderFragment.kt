package com.example.tyfarris.reminderapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
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
import android.widget.RadioGroup
import android.widget.Toast
import java.util.*

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

        //radio button
        val radioGroup = view.findViewById(R.id.buttonGroup) as RadioGroup
        var selectedCategory : Float = model.lstDirectory[model.selectedListPosition].reminderList[model.selectedReminderPos].category

        //updated values
        var updatedEvent = ""
        var updatedPlace = ""
        var updatedDescription = ""

        //booleans to keep track of which is updated
        var hasEventChanged = false
        var hasPlaceChanged = false
        var hasDescriptionChanged = false

        //references to edit texts on fragment
        event.setText(model.lstDirectory[model.selectedListPosition].reminderList[model.selectedReminderPos].event)
        time.setText(model.lstDirectory[model.selectedListPosition].reminderList[model.selectedReminderPos].time)
        date.setText(model.lstDirectory[model.selectedListPosition].reminderList[model.selectedReminderPos].date)
        place.setText(model.lstDirectory[model.selectedListPosition].reminderList[model.selectedReminderPos].place)
        description.setText(model.lstDirectory[model.selectedListPosition].reminderList[model.selectedReminderPos].description)

        //show the radio button is selected
        when (selectedCategory) {

            1.0f -> radioGroup.check(R.id.socialBtn)
            2.0f -> radioGroup.check(R.id.workBtn)
            3.0f -> radioGroup.check(R.id.healthBtn)
        }

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

            if (hasPlaceChanged) {
                model.lstDirectory[model.selectedListPosition].reminderList[model.selectedReminderPos].place = updatedPlace
            }

            if (hasDescriptionChanged) {
                model.lstDirectory[model.selectedListPosition].reminderList[model.selectedReminderPos].description = updatedDescription
            }

            model.lstDirectory[model.selectedListPosition].reminderList[model.selectedReminderPos].category = selectedCategory

            val fm = activity?.supportFragmentManager
            fm?.popBackStack ("editReminder", FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }

        cancelBtn?.setOnClickListener{
            val fm = activity?.supportFragmentManager
            fm?.popBackStack ("editReminder", FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }

        //when date is edited using time picker
        date.setOnClickListener{
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val mDatePicker: DatePickerDialog
            mDatePicker = DatePickerDialog(activity, DatePickerDialog.OnDateSetListener {
                datePicker, selectedYear, selectedMonth, selectedDay ->

                model.lstDirectory[model.selectedListPosition].reminderList[model.selectedReminderPos].date = (1 + month).toString() + "/" + day.toString() + "/" + year.toString()
                date.setText((1 + selectedMonth).toString() + "/" + selectedDay.toString() + "/" + selectedYear.toString())},
                    year, month, day)

            mDatePicker.show()
        }


        //when time is edited using time picker
        time.setOnClickListener{
            //            val newFragment = TimePickerFragment()
//            newFragment.show(fragmentManager, "timePicker")

            val currentTime = Calendar.getInstance()
            val hour = currentTime.get(Calendar.HOUR_OF_DAY)
            val minute = currentTime.get(Calendar.MINUTE)
            var hourAmPm : Int
            var amPm : String

            val mTimePicker: TimePickerDialog
            mTimePicker = TimePickerDialog(activity, TimePickerDialog.OnTimeSetListener {
                timePicker, selectedHour, selectedMinute ->

                if (selectedHour >= 12){
                    amPm = "PM"

                    if (selectedHour == 12) {
                        hourAmPm = selectedHour
                    }
                    else {
                        hourAmPm = selectedHour % 12
                    }
                }
                else {
                    amPm = "AM"
                    if (selectedHour == 0) {
                        hourAmPm = 12
                    }
                    else {
                        hourAmPm = selectedHour
                    }
                }

                model.lstDirectory[model.selectedListPosition].reminderList[model.selectedReminderPos].time = hourAmPm.toString() + ":" + selectedMinute + " " + amPm

                if (selectedMinute < 10)
                    time.setText(hourAmPm.toString() + ":0" + selectedMinute + " " + amPm)
                else
                    time.setText(hourAmPm.toString() + ":" + selectedMinute + " " + amPm)

            }, hour, minute, false)

            mTimePicker.show()
        }

        //when the category button is selected
        radioGroup.setOnCheckedChangeListener{
            group, checkedId ->
            when (checkedId) {
                R.id.socialBtn -> {
                    selectedCategory = 1.0f
                }

                R.id.workBtn -> {
                    selectedCategory = 2.0f
                }

                R.id.healthBtn -> {
                    selectedCategory = 3.0f
                }
            }
        }

        return view
    }
}