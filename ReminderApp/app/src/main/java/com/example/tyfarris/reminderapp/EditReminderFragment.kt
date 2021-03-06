package com.example.tyfarris.reminderapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.text.SimpleDateFormat
import java.util.*

class EditReminderFragment : Fragment() {
    private lateinit var model: MyModelView
    var tts:TextToSpeech? = null

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
        val doneBtn = view.findViewById<Button>(R.id.buttonDoneReminder)

        //if the task is not done display the view button
        if (!model.lstDirectory[model.selectedListPosition].reminderList[model.selectedReminderPos].isDone)
            doneBtn.visibility = View.VISIBLE

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

        //for the date and time in a reminder
        var dataFormat = SimpleDateFormat("MMM d, yyyy")
        var timeFormat = SimpleDateFormat("hh:mm")
        var dateCalendar : Calendar = Calendar.getInstance()
        var amPm : String = model.lstDirectory[model.selectedListPosition].reminderList[model.selectedReminderPos].am_pm

        //spinner on the details xml
        val spinnerValues = mutableListOf("Never", "Day of", "30 minutes before", "1 hour before", "1 day before")
        val notifySpinner = view.findViewById<Spinner>(R.id.spinner)
        var notifyValue : Int = model.lstDirectory[model.selectedListPosition].reminderList[model.selectedReminderPos].notification

        //references to edit texts on fragment
        event.setText(model.lstDirectory[model.selectedListPosition].reminderList[model.selectedReminderPos].event)
        date.setText(dataFormat.format(model.lstDirectory[model.selectedListPosition].reminderList[model.selectedReminderPos].date))
        time.setText(timeFormat.format(model.lstDirectory[model.selectedListPosition].reminderList[model.selectedReminderPos].date) + " " + amPm)
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
                dateCalendar.set(selectedYear, selectedMonth, selectedDay)
                date.setText(dataFormat.format(dateCalendar.time))
                model.lstDirectory[model.selectedListPosition].reminderList[model.selectedReminderPos].date = dateCalendar.time
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
                model.lstDirectory[model.selectedListPosition].reminderList[model.selectedReminderPos].date = dateCalendar.time
                model.lstDirectory[model.selectedListPosition].reminderList[model.selectedReminderPos].am_pm = amPm
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

        //to say great job when done button is pressed
        tts = TextToSpeech(activity?.applicationContext, TextToSpeech.OnInitListener { status ->
            if (status != TextToSpeech.ERROR) {
                tts!!.setLanguage(Locale.UK)
            }
        })


        //when the task is done add a point
        doneBtn.setOnClickListener{
            model.progress = model.progress + 20
            tts!!.speak("Great Job!", TextToSpeech.QUEUE_FLUSH, null, null)
            model.lstDirectory[model.selectedListPosition].reminderList[model.selectedReminderPos].isDone = true
            doneBtn.visibility = View.INVISIBLE
        }

        notifySpinner.adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, spinnerValues)
        notifySpinner.setSelection(notifyValue)
        //to get the selected Item
        notifySpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                model.lstDirectory[model.selectedListPosition].reminderList[model.selectedReminderPos].notification = position
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
}