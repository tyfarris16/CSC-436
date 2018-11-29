package com.example.tyfarris.reminderapp

import android.arch.lifecycle.ViewModel

class MyModelView : ViewModel() {

    var lstDirectory : MutableList<ReminderList> = mutableListOf()

    data class ReminderList(val listName: String,
                            val reminderList: MutableList<Reminder>)

    data class Reminder(val event: String,
                        val date: String,
                        val time: String,
                        val place: String,
                        val description: String)

    fun addReminderList(reminderLstName: String){
        val lst = ReminderList(reminderLstName, mutableListOf())
        lstDirectory.add(lst)
    }

//    fun getDirectoryList(): MutableList<ReminderList> {
//        if (!::lstDirectory.isInitialized) {
//            lstDirectory = mutableListOf()
//        }
//        return lstDirectory
//    }
}