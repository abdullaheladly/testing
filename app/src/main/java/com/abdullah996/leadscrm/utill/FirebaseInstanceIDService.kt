package com.abdullah996.leadscrm.utill

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import com.abdullah996.leadscrm.R
import com.abdullah996.leadscrm.ui.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class FirebaseInstanceIDService()  : FirebaseMessagingService() {


    private var count = 0

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)

    }


    override fun onMessageReceived(remoteMessage: RemoteMessage) { //Here notification is recieved from server
        try {
            sendNotification(
                remoteMessage.notification!!.title,
                remoteMessage.notification!!.body
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun sendNotification(
        title: String?,
        messageBody: String?,
        image: Bitmap? = null
    ) {
        //Add Any key-value to pass extras to intent
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.action = "Open_History"
        val pendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val defaultSoundUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val mNotifyManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        //For Android Version Orio and greater than orio.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel("Leadscrm", "Leadscrm", importance)
            mChannel.description = messageBody
            mChannel.enableLights(true)
            mChannel.setSound(defaultSoundUri, Notification.AUDIO_ATTRIBUTES_DEFAULT)
            mChannel.lightColor = Color.RED
            mChannel.enableVibration(true)
            mChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            mNotifyManager.createNotificationChannel(mChannel)
        }

        //For Android Version lower than oreo.
        val mBuilder: NotificationCompat.Builder = NotificationCompat.Builder(this, "meshlwa7dk")
        mBuilder.setContentTitle(title)
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.ic_logo)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
            .setChannelId("Leadscrm").priority = NotificationCompat.PRIORITY_HIGH

        if (image != null)
            mBuilder.setLargeIcon(image)
                .setStyle(
                    NotificationCompat.BigPictureStyle()
                        .bigPicture(image)
                        .bigLargeIcon(null)
                )
        mNotifyManager.notify(count, mBuilder.build())
        count++

    }


}