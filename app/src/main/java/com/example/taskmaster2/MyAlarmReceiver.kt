package com.example.taskmaster2

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.widget.Toast
import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat

class MyAlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("NOTIF", "entered here!")
        Toast.makeText(context, intent.getStringExtra("title") + " " + intent.getStringExtra("content"), Toast.LENGTH_LONG).show()


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


            // Create the NotificationChannel
            val name = "Alarm"
            val descriptionText = "Alarm Desc"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel("channel", name, importance)
            mChannel.description = descriptionText
            val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }

        val notificationIntent = Intent(context, Inicio::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 2233, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notification = NotificationCompat.Builder(context!!, "channel")
            .setSmallIcon(R.drawable.taskimagen)
            .setContentTitle(intent!!.getStringExtra("title"))
            .setContentText(intent!!.getStringExtra("content"))
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        val manager = context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(100, notification)




    }
}