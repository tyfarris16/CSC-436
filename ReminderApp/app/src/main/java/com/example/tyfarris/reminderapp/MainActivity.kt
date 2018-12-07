package com.example.tyfarris.reminderapp

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import android.widget.TextView
import kotlinx.android.synthetic.main.nav_header_main.*
import org.w3c.dom.Text


class MainActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener {

    lateinit var model : MyModelView

//    //For notifications
//    private val mNotificationTime = Calendar.getInstance().timeInMillis + 5000 //Set after 5 seconds from the current time.
//    private var mNotified = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        //To make the info logo clickable
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar_info_logo.setOnClickListener{
            this.supportFragmentManager?.beginTransaction()?.replace(R.id.fragment_container, EditListNameFragment())?.addToBackStack("editListName")?.commit()
        }

        //load view model
        model = ViewModelProviders.of(this).get(MyModelView::class.java)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar,
                        R.string.navigation_drawer_open, R.string.navigation_drawer_close)

        nav_view.setNavigationItemSelectedListener(this)

        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, RemindersFragment()).commit()
            nav_view.setCheckedItem(R.id.nav_reminders)
        }

//        //for notifications
//        //getIntent().getExtras().getString("notified").toBoolean()
//        if (!mNotified) {
//            NotificationUtils().setNotification(mNotificationTime, this@MainActivity)
//        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        }
        else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_profile -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, ProfileFragment()).addToBackStack("profile").commit()
            }
            R.id.nav_reminders -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, RemindersFragment()).addToBackStack("reminders").commit()
            }
            R.id.nav_life_balance -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, LifeBalanceFragment()).addToBackStack("lifeBalance").commit()
            }
            R.id.nav_calendar -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, CalendarFragment()).addToBackStack("calendar").commit()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}