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
import kotlinx.android.synthetic.main.details.*
import android.app.TimePickerDialog
import android.widget.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class AddReminderFragment : Fragment() {

    private lateinit var model: MyModelView
    //For notifications
    //private val mNotificationTime = Calendar.getInstance().timeInMillis + 5000 //Set after 5 seconds from the current time.
    private var mNotificationTime = Calendar.getInstance().timeInMillis
    private var mNotified = false

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
        val radioGroup = view.findViewById(R.id.buttonGroup) as RadioGroup

        val event = view.findViewById<EditText>(R.id.editEvent)
        val time = view.findViewById<EditText>(R.id.editTime)
        val date= view.findViewById<EditText>(R.id.editDate)
        val place= view.findViewById<EditText>(R.id.editPlace)
        val description = view.findViewById<EditText>(R.id.editDescription)
        var selectedCategory : Float = -1.0f


        //to store the date and time in reminder
        var dateCalendar : Calendar = Calendar.getInstance()
        var dataFormat = SimpleDateFormat("MMM d, yyyy")
        var timeFormat = SimpleDateFormat("hh:mm")
        var amPm : String = getAmPm(Calendar.HOUR)

        //spinner on the details xml
        val spinnerValues = mutableListOf("Never", "Day of", "30 minutes before", "1 hour before", "1 day before")
        val notifySpinner = view.findViewById<Spinner>(R.id.spinner)
        var notifyValue : Int = 0

        var notifyBefore : Long

        saveBtn?.setOnClickListener{
            //add reminder to list
            model.lstDirectory[model.selectedListPosition].reminderList.add(MyModelView.Reminder(event.text.toString(),
                    dateCalendar.time, place.text.toString(),
                    description.text.toString(), selectedCategory,
                    amPm, false, notifyValue))

            //for notifications
            //getIntent().getExtras().getString("notified").toBoolean()
            // notifyBefore = getNotifyBy(notifyValue)

            mNotificationTime = Calendar.getInstance().timeInMillis

            if (!mNotified && notifyValue > 0) {
                NotificationUtils().setNotification(mNotificationTime, activity!!, event.text.toString(), description.text.toString())
            }

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
                dateCalendar.set(selectedYear, selectedMonth, selectedDay)
                date.setText(dataFormat.format(dateCalendar.time))
                 },
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

            val mTimePicker: TimePickerDialog
            mTimePicker = TimePickerDialog(activity, TimePickerDialog.OnTimeSetListener {
                timePicker, selectedHour, selectedMinute ->

                amPm = getAmPm(selectedHour)
                dateCalendar.set(Calendar.HOUR, selectedHour)
                dateCalendar.set(Calendar.MINUTE, selectedMinute)
                time.setText(timeFormat.format(dateCalendar.time) + " " + amPm)

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

        notifySpinner.adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, spinnerValues)
        //to get the selected Item
        notifySpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                notifyValue= position
            }
        }

        return view
    }

    fun getAmPm(selectedHour: Int) : String {
        if (selectedHour >= 12){
            return "PM"
        }
        else {
            return "AM"
        }
    }

    fun getNotifyBy(pos : Int) : Long {
        val thirtyMinutes:Long  = 1800000
        val oneHour:Long  = 3600000
        val oneDay:Long  = 86400000

        when (pos) {
            2 -> return thirtyMinutes
            3 -> return oneHour
            4 -> return oneDay
        }
        return -1
    }
}