package com.example.tyfarris.reminderapp

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import kotlinx.android.synthetic.main.recycler_item.view.*
import java.util.*

class MyAdapter2(val fragment: Fragment, val model: MyModelView, val currentPos: Int, val myDataset: MyModelView.ReminderList) :
        RecyclerView.Adapter<MyAdapter2.MyViewHolder>() {

    lateinit var checkMark : ImageView

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    inner class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        init {
            checkMark = view.findViewById(R.id.check_mark)

            view.setOnClickListener {
                model.selectedReminderPos = adapterPosition
                fragment.activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragment_container, EditReminderFragment())?.addToBackStack("editReminder")?.commit()
            }

            view.setOnLongClickListener{
                model.lstDirectory[currentPos].reminderList.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
                notifyItemRangeChanged(adapterPosition, myDataset.reminderList.size)
                return@setOnLongClickListener true
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyAdapter2.MyViewHolder {
        // create a new view
        // could also inflate android.R.layout.simple_list_item_1 if desired
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_item, parent, false)
        // set the view's size, margins, paddings and layout parameters

        return MyViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.view.text_view_reminders.text = myDataset.reminderList[position].event

        if (myDataset.reminderList[position].isDone){
            checkMark.visibility = View.VISIBLE
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.reminderList.size
}