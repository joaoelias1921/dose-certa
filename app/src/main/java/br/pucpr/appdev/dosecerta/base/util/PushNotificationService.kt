package br.pucpr.appdev.dosecerta.base.util

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import br.pucpr.appdev.dosecerta.R

class PushNotificationService: FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        // Respond to received messages
        val title = message.notification?.title
        val body = message.notification?.body
        val channelId = "HEADS_UP_NOTIFICATIONS"

        createNotificationChannel()

        val notification = Notification
            .Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.logo_dose_certa)
            .setAutoCancel(true)
            .build()

        if (ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.POST_NOTIFICATIONS
        ) != PackageManager.PERMISSION_GRANTED) {
            // If permission is not granted, simply return.
            // The user has likely denied the notification permission.
            return
        }

        NotificationManagerCompat.from(this).notify(1, notification)
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            "HEADS_UP_NOTIFICATIONS",
            "MyNotifications",
            NotificationManager.IMPORTANCE_HIGH
        )
        val notificationManager = getSystemService(
            NOTIFICATION_SERVICE
        ) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}