package com.example.tyfarris.lab4

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_item_detail.*
import kotlinx.android.synthetic.main.add_event.*
import kotlinx.android.synthetic.main.add_event.view.*
import kotlinx.android.synthetic.main.detail_layout.view.*
import kotlinx.android.synthetic.main.item_detail.view.*

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a [ItemListActivity]
 * in two-pane mode (on tablets) or a [ItemDetailActivity]
 * on handsets.
 */
class AddDialogFragment : Fragment() {

    /**
     * The dummy content this fragment is presenting.
     */
    var enteredEvent = "";
    var enteredDate = "";
    var enteredTime = "";
    var enteredLocation = "";

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.add_event, container, false)
        val model = ViewModelProviders.of(this).get(MyViewModel::class.java)

        rootView.cancelButton.setOnClickListener {
            activity?.finish()
        }

        rootView.addEvent.addTextChangedListener(object: TextWatcher {

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                enteredEvent = addEvent.text.toString()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        rootView.addTime.addTextChangedListener(object: TextWatcher {

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                enteredTime = addTime.text.toString()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        rootView.addDate.addTextChangedListener(object: TextWatcher {

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                enteredDate = addDate.text.toString()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        rootView.addLocation.addTextChangedListener(object: TextWatcher {

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                enteredLocation = addLocation.text.toString()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        rootView.addButton.setOnClickListener {
            model.addItem(model.createScheduledEvent(enteredEvent,
                    enteredDate, enteredTime, enteredLocation))

            activity?.finish()
        }

        return rootView
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }
}
