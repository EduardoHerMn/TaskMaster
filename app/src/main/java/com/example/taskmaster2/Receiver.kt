package com.example.taskmaster2

import android.content.BroadcastReceiver
import android.R
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import java.util.*


class Receiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        Log.d("Alarm",  "receive "+ Date().toString())


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


            // Create the NotificationChannel
            val name = "Alarm"
            val descriptionText = "Alarm Desc"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel("channel", name, importance)
            mChannel.description = descriptionText
            val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)


            val notification = NotificationCompat.Builder(context!!, "channel")
                .setSmallIcon(R.drawable.alert_dark_frame)
                .setContentTitle(intent!!.getStringExtra("title"))
                .setContentText(intent!!.getStringExtra("content"))
                .build()

            val manager = context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.notify(0, notification)


        }else{
            val notification = Notification.Builder(context)
                .setSmallIcon(R.drawable.alert_dark_frame)
                .setContentTitle(intent!!.getStringExtra("title"))
                .setContentText(intent!!.getStringExtra("content"))
                .build()

            val manager = context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.notify(0, notification)

        }



    }

}