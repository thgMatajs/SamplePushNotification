package com.thgcode.fcm

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    companion object {
        private const val TAG = "FCM_Service"
    }

    override fun onNewToken(token: String?) {
        Log.i(TAG, token)

        val firebaseMessaging = FirebaseMessaging.getInstance()
        firebaseMessaging.subscribeToTopic("MAIN")
        firebaseMessaging.isAutoInitEnabled = true
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val notification = remoteMessage.notification

        Log.i(TAG, "Message ID: ${remoteMessage.messageId}")
        Log.i(TAG, "Data  Message: ${remoteMessage.data}")
        Log.i(TAG, "Notification Message: $remoteMessage")

        notification?.let {
            val title = it.title ?: ""
            val body = it.body ?: ""
            val data = remoteMessage.data

            Log.i(TAG, "Notification Title: $title")
            Log.i(TAG, "Notification Body: $body")
            Log.i(TAG, "Notification Data: $data")

            NotificationCreation.create(this, title, body)
        }
    }
}