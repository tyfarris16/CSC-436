package com.example.tyfarris.lab4

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.NavUtils.navigateUpTo
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.tyfarris.lab4.dummy.TFarDatastore
import kotlinx.android.synthetic.main.activity_item_detail.*
import kotlinx.android.synthetic.main.add_event.*
import kotlinx.android.synthetic.main.add_event.view.*
import kotlinx.android.synthetic.main.detail_layout.*
import kotlinx.android.synthetic.main.detail_layout.view.*
import kotlinx.android.synthetic.main.item_detail.view.*
//import com.example.tyfarris.lab4.ItemDetailFragment.OnDataPass



/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a [ItemListActivity]
 * in two-pane mode (on tablets) or a [ItemDetailActivity]
 * on handsets.
 */
class ItemDetailFragment : Fragment() {
    /**
     * The dummy content this fragment is presenting.
     */
//    public interface OnDataPass {
//        fun onDataPass(data: String)
//    }

    private var item: TFarDatastore.scheduledEvent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                // Load the dummy content specified by the fragment
                // arguments. In a real-world scenario, use a Loader
                // to load content from a content provider.
                item = TFarDatastore.ITEM_MAP[it.getString(ARG_ITEM_ID)]
                activity?.toolbar_layout?.title = item?.sEvent
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.detail_layout, container, false)

        var changedEvent = item?.sEvent
        var changedDate = item?.sDate
        var changedTime = item?.sTime
        var changedLocation = item?.sLocation

        // Show the dummy content as text in a TextView.
        item?.let {
            rootView.editEvent.setText(it.sEvent)
            rootView.editDate.setText(it.sDate)
            rootView.editTime.setText(it.sTime)
            rootView.editLocation.setText(it.sLocation)
        }

        rootView.cancelButton2.setOnClickListener {
            activity?.finish()
        }


        rootView.editEvent.addTextChangedListener(object: TextWatcher {

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                changedEvent = editEvent.text.toString()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        rootView.editDate.addTextChangedListener(object: TextWatcher {

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                changedDate = editDate.text.toString()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        rootView.editTime.addTextChangedListener(object: TextWatcher {

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                changedTime = editTime.text.toString()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })


        rootView.editLocation.addTextChangedListener(object: TextWatcher {

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                changedLocation = editLocation.text.toString()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        rootView.saveButton.setOnClickListener {
            //Toast.makeText(activity, "Please long press the key", Toast.LENGTH_SHORT ).show();
            TFarDatastore.ITEMS.remove(item)
            TFarDatastore.ITEM_MAP.remove(item?.sEvent)

            //val index = TFarDatastore.ITEMS.indexOf(item)
            var modifiedEvent = TFarDatastore.createScheduledEvent(changedEvent.toString(),
                    changedDate.toString(),
                    changedTime.toString(),
                    changedLocation.toString())
            TFarDatastore.addItem(modifiedEvent)
            activity?.finish()
        }

        rootView.deleteButton.setOnClickListener{
            TFarDatastore.ITEMS.remove(item)
            TFarDatastore.ITEM_MAP.remove(item?.sEvent)
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
