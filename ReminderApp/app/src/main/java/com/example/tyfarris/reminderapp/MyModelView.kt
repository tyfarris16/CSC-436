package com.example.tyfarris.reminderapp

import android.arch.lifecycle.ViewModel
import android.widget.ImageView
import java.io.Serializable
import java.util.*

class MyModelView : ViewModel() {

    var lstDirectory : MutableList<ReminderList> = mutableListOf()
    var selectedListPosition : Int = -1
    var selectedReminderPos : Int = -1
    var progress: Int = 0
    var level : Int = 0

    data class ReminderList(var listName: String,
                            val reminderList: MutableList<Reminder>) : Serializable

    data class Reminder(var event: String,
                        var date: Date,
                        var place: String,
                        var description: String,
                        var category : Float,
                        var am_pm : String,
                        var isDone : Boolean)

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