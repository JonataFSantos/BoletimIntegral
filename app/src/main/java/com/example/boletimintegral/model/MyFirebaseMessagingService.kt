package com.example.boletimintegral.model

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService( ){

    class MyFirebaseMessagingService : FirebaseMessagingService() {

        override fun onMessageReceived(remoteMessage: RemoteMessage) {


            Log.d(TAG, "From: ${remoteMessage.from}")

            // Verifica se a mensagem contém um payload de dados
            if (remoteMessage.data.isNotEmpty()) {
                Log.d(TAG, "Message data payload: ${remoteMessage.data}")

                // Se a tarefa for de longa duração, use o WorkManager
                if (true) {  // Adapte essa condição conforme necessário
                    scheduleJob()  // Implementar função para tarefas longas
                } else {
                    handleNow()  // Processa a mensagem em menos de 10 segundos
                }
            }

            // Verifica se a mensagem contém um payload de notificação
            remoteMessage.notification?.let {
                Log.d(TAG, "Message Notification Body: ${it.body}")
            }

            // Aqui você pode gerar suas próprias notificações, se necessário
            sendNotification(remoteMessage.notification?.body)
        }

        private fun scheduleJob() {
            // Implementação de uma tarefa longa usando o WorkManager
        }

        private fun handleNow() {
            // Implementação de tarefa curta
        }

        private fun sendNotification(messageBody: String?) {
            // Código para gerar notificações
        }

        companion object {
            private const val TAG = "MyFirebaseMsgService"
        }
    }


}