package com.example.boletimintegral.model

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.boletimintegral.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService( ){

    class MyFirebaseMessagingService : FirebaseMessagingService() {

        override fun onMessageReceived(remoteMessage: RemoteMessage) {


            Log.d(TAG, "From: ${remoteMessage.from}")

            // Verifica se a mensagem contém um payload de dados
            if (remoteMessage.data.isNotEmpty()) {
                Log.d(TAG, "Message data payload: ${remoteMessage.data}")


            }

            // Verifica se a mensagem contém um payload de notificação
            remoteMessage.notification?.let {
                Log.d(TAG, "Message Notification Body: ${it.body}")
            }

            // Aqui você pode gerar suas próprias notificações, se necessário
            remoteMessage.notification?.let { sendNotification(it) }
        }


        private fun sendNotification(message : RemoteMessage.Notification) {
            // Código para gerar notificações


            var builder = NotificationCompat.Builder(this, "CHANNEL_ID")
                .setSmallIcon(R.drawable.baseline_message_24)
                .setContentTitle(message.title)
                .setContentText(message.body)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                val channel = NotificationChannel(
                    "Canal 1",
                    "Test",
                    NotificationManager.IMPORTANCE_DEFAULT
                )


                val notificationManager : NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as
                            NotificationManager



                notificationManager.createNotificationChannel(channel)




            }



        }

        companion object {
            private const val TAG = "MyFirebaseMsgService"
        }
    }


}