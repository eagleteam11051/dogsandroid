package com.tbm.dogs.Services

import android.app.ActivityManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.tbm.dogs.R
import com.tbm.dogs.activities.congviec.dangco.ViecDangCo
import com.tbm.dogs.activities.main.Main
import com.tbm.dogs.activities.thongbao.ChiTietThongBao
import android.app.NotificationChannel
import android.os.Build





class MyFirebaseMessaging : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)
        createNotificationChannel()
        showNotification(remoteMessage!!.notification!!)
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "shipx"
            val description = "dogs"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("id_channel", name, importance)
            channel.description = description
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager!!.createNotificationChannel(channel)
        }
    }

    private fun showNotification(notification: RemoteMessage.Notification) {
        var it:Intent
        if(notification.tag == "0"){
            // thong bao noi bo
            it = Intent(this, ChiTietThongBao::class.java)
            it.putExtra("title", notification.title)
            it.putExtra("body", notification.body)
        }else{
            // thong bao co job new
            it = Intent(this, ViecDangCo::class.java)
        }

        val am = this.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val taskInfo = am.getRunningTasks(1)
        Log.e("topActivity", "CURRENT Activity ::" + taskInfo[0].topActivity.className)
        if(taskInfo[0].topActivity.className == "com.tbm.dogs.activities.congviec.ViecDangCo" && notification.tag == "1"){
            startActivity(Intent(this, ViecDangCo::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP))
        }
        if(taskInfo[0].topActivity.className == "com.tbm.dogs.activities.main.Main" && notification.tag == "1"){
            startActivity(Intent(this,Main::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP))
        }

        val pendingIntent = PendingIntent.getActivity(this, 0, it, PendingIntent.FLAG_ONE_SHOT)
        val builder = NotificationCompat.Builder(this)
                .setChannelId("id_channel")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(notification.title)
                .setContentText(notification.body)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(0, builder.build())
    }

}
