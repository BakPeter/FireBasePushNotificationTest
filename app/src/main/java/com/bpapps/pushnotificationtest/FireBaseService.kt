package com.bpapps.pushnotificationtest

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random

private const val TAG = "TAG.FireBaseService"
private const val CHANEL_ID = "TEST_PUSH_NOTIFICATION_APP_CHANNEL_ID"
private const val CHANEL_NAME = "TEST_PUSH_NOTIFICATION_CHANEL_NAME"

class FireBaseService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)

        Log.d(TAG, "new token = $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChanel(notificationManager)
        }

        val notification = NotificationCompat.Builder(this, CHANEL_ID)
            .setContentTitle(message.data["title"])
            .setContentText(message.data["message"])
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setAutoCancel(true)
            .build()


        notificationManager.notify(Random.nextInt(), notification)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChanel(notificationManager: NotificationManager) {
        val chanel = NotificationChannel(
            CHANEL_ID,
            CHANEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        )

        notificationManager.createNotificationChannel(chanel)
    }
}