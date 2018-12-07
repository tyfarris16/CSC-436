package com.example.tyfarris.reminderapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val service = Intent(context, NotificationService::class.java)
        service.putExtra("reason", intent.getStringExtra("reason"))
        service.putExtra("timestamp", intent.getLongExtra("timestamp", 0))
        service.putExtra("event", intent?.extras!!.get("event") as String)
        service.putExtra("description", intent?.extras!!.get("description") as String)

        service.data = Uri.parse("custom://" + System.currentTimeMillis())
        context.startService(service)
    }
}