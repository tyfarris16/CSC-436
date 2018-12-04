package com.example.tyfarris.reminderapp

import android.app.DatePickerDialog
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
import kotlinx.android.synthetic.main.details.*
import android.widget.TimePicker
import android.app.TimePickerDialog
import java.text.DateFormat
import java.util.*


class AddReminderFragment : Fragment() {

    private lateinit var model: MyModelView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model = activity.run {
            ViewModelProviders.of(activity!!).get(MyModelView::class.java)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.details, container, false)
        val saveBtn = view.findViewById<Button>(R.id.buttonSaveReminder)
        val cancelBtn = view.findViewById<Button>(R.id.buttonCancelReminder)

        val event = view.findViewById<EditText>(R.id.editEvent)
        val time = view.findViewById<EditText>(R.id.editTime)
        val date= view.findViewById<EditText>(R.id.editDate)
        val place= view.findViewById<EditText>(R.id.editPlace)
        val description = view.findViewById<EditText>(R.id.editDescription)

        saveBtn?.setOnClickListener{
            //add reminder to list
            model.lstDirectory[model.selectedListPosition].reminderList.add(MyModelView.Reminder(event.text.toString(),
                    date.text.toString(), time.text.toString(), place.text.toString(), description.text.toString()))

            val fm = activity?.supportFragmentManager
            fm?.popBackStack ("addReminder", FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }

        cancelBtn?.setOnClickListener{
            val fm = activity?.supportFragmentManager
            fm?.popBackStack ("addReminder", FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }

        //when date is edited using date picker
        date.setOnClickListener{
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val mDatePicker: DatePickerDialog
            mDatePicker = DatePickerDialog(activity, DatePickerDialog.OnDateSetListener {
                datePicker, selectedYear, selectedMonth, selectedDay ->
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

                if (selectedMinute < 10)
                    time.setText(hourAmPm.toString() + ":0" + selectedMinute + " " + amPm)
                else
                time.setText(hourAmPm.toString() + ":" + selectedMinute + " " + amPm)

            }, hour, minute, false)

            mTimePicker.show()
        }
        return view
    }
}