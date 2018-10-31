package com.example.tyfarris.lab4.dummy

import java.util.ArrayList
import java.util.HashMap
import java.util.Random

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object TFarDatastore {

//    val arrDay = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
//    val arrMonth = listOf("Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sep", "Oct")
//    val arrYear = listOf(2000, 2001, 2002, 2003, 2004, 2005, 2006, 2007, 2008, 2009, 2010)
//    val arrTime = listOf("8am", "9am", "10am", "11am", "12pm", "1pm", "2pm", "3pm", "4pm", "5pm")


    /**
     * An array of sample (dummy) items.
     */
    val ITEMS: MutableList<scheduledEvent> = ArrayList()

    /**
     * A map of sample (dummy) items, by ID.
     */
    val ITEM_MAP: MutableMap<String, scheduledEvent> = HashMap()

    //private val COUNT = 20

    init {

        // Add some sample items.
//        for (i in 1..COUNT)
            addItem(createScheduledEvent("Event", "Date", "Time", "Location"))
    }

    fun addItem(item: scheduledEvent) {
        ITEMS.add(item)
        ITEM_MAP.put(item.sEvent, item)
    }

    fun createScheduledEvent(tempEvent: String,
                                     tempDate: String,
                                     tempTime: String,
                                     tempLocation: String): scheduledEvent {
//        return scheduledEvent(arrDay.get(position).toString(),
//                arrMonth.get(position),
//                arrYear.get(position).toString(),
//                arrTime.get(position))

        return scheduledEvent(tempEvent,
                tempDate,
                tempTime,
                tempLocation)
    }

//    private fun makeDetails(position: Int): String {
//        val builder = StringBuilder()
//        builder.append("Details about Item: ").append(position)
//        for (i in 0..position - 1) {
//            builder.append("\nMore details information here.")
//        }
//        return builder.toString()
//    }

    /**
     * A dummy item representing a piece of content.
     * originally : id, content, details, time
     */
    data class scheduledEvent(var sEvent: String, var sDate: String, var sTime: String, var sLocation: String) {
        override fun toString(): String = sEvent

    }
}
