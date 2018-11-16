package com.example.tyfarris.lab4

import android.arch.lifecycle.LifecycleObserver
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_item_list.*
import kotlinx.android.synthetic.main.detail_layout.*
import kotlinx.android.synthetic.main.detail_layout.view.*
import kotlinx.android.synthetic.main.item_list_content.view.*
import kotlinx.android.synthetic.main.item_list.*

import android.arch.lifecycle.ViewModelProviders
import android.arch.lifecycle.Observer
import android.util.EventLog
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [ItemDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class ItemListActivity : AppCompatActivity() {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = FirebaseDatabase.getInstance()
        database.setPersistenceEnabled(true)

        val model = ViewModelProviders.of(this).get(MyViewModel::class.java)

        model.scheduleRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                //println("Snapshot type is:" + dataSnapshot.javaClass.name)

                model.viewModelItems.value?.clear()

                dataSnapshot.children.forEach {
                    val event = it.getValue(MyViewModel.scheduledEvent::class.java)
                    //model.viewModelItems.value?.add(event!!)
                    model.addItem(event!!)
                }

            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
            }
        })


        model.viewModelItems.observe(this, Observer<MutableList<MyViewModel.scheduledEvent>>{
            item_list.adapter.notifyDataSetChanged()
            // update UI
            Log.d("MADEIT", "Observer has been called")
        })


        setContentView(R.layout.activity_item_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        val fab: View = findViewById(R.id.fab)

        if (item_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }

        setupRecyclerView(item_list)

        fab.setOnClickListener {
            val i = Intent(this, AddDialogActivity::class.java)
            startActivity(i)
            setupRecyclerView(item_list)
//            Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null)
//                    .show()
        }
    }

    override fun onResume() {
        super.onResume()
        item_list.adapter.notifyDataSetChanged()
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        val model = ViewModelProviders.of(this).get(MyViewModel::class.java)
        recyclerView.adapter = SimpleItemRecyclerViewAdapter(this, model.ITEMS, twoPane)
        recyclerView.adapter.notifyDataSetChanged()
    }

    class SimpleItemRecyclerViewAdapter(private val parentActivity: ItemListActivity,
                                        private val values: List<MyViewModel.scheduledEvent>,
                                        private val twoPane: Boolean) :
            RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        private val onClickListener: View.OnClickListener

        init {
            onClickListener = View.OnClickListener { v ->
                val item = v.tag as MyViewModel.scheduledEvent
                if (twoPane) {
                    val fragment = ItemDetailFragment().apply {
                        arguments = Bundle().apply {
                            putString(ItemDetailFragment.ARG_ITEM_ID, item.sEvent)
                        }
                    }
                    parentActivity.supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.item_detail_container, fragment)
                            .commit()
                } else {
                    val intent = Intent(v.context, ItemDetailActivity::class.java).apply {
                        putExtra(ItemDetailFragment.ARG_ITEM_ID, item.sEvent)
                    }
                    val model = ViewModelProviders.of(parentActivity).get(MyViewModel::class.java)
                    model.position = item.sEvent

                    v.context.startActivity(intent)
                }
                notifyDataSetChanged()
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_list_content, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = values[position]
            holder.eventView.text = item.sEvent
            holder.dateView.text = item.sDate
            holder.timeView.text = item.sTime
            holder.locationView.text = item.sLocation

            with(holder.itemView) {
                tag = item
                setOnClickListener(onClickListener)
            }
        }

        override fun getItemCount() = values.size

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val eventView: TextView = view.textView1
            val dateView: TextView = view.textView2
            val timeView: TextView = view.textView3
            val locationView: TextView = view.textView4
        }
    }
}
