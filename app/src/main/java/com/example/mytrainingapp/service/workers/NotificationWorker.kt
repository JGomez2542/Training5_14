package com.example.mytrainingapp.service.workers

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.mytrainingapp.R
import com.example.mytrainingapp.common.*

/**
 * This worker will be used to send a notification to the user.
 */
class NotificationWorker(private val context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    //Starting in Android 8.0 Oreo (API Level 26), all notifications must be associated with a notification channel
    //we create the channel here.
    init {
        val name = context.getString(R.string.notification_channel_name)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(MESSAGE_CHANNEL, name, NotificationManager.IMPORTANCE_HIGH)
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    @SuppressLint("NewApi")
    override fun doWork(): Result {
        val data = inputData.getString(WORKER_RANDOM_TOAST)

        //Tapping on the notification itself will direct the user to the application's main activity
        val launchIntent = context.packageManager.getLaunchIntentForPackage(context.packageName)
        val launchPendingIntent = PendingIntent.getActivity(
            context,
            LAUNCHER_PENDING_INTENT_ID,
            launchIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        //Tapping on the WorkManager action will direct the user to the WorkManager web page
        val workManagerIntent = Intent(Intent.ACTION_VIEW)
        workManagerIntent.data = Uri.parse(WORK_MANAGER_URL)
        val workManagerPendingIntent = PendingIntent.getActivity(
            context,
            WORK_MANAGER_PENDING_INTENT_ID,
            workManagerIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        //Tapping on the PendingIntent action will direct the user to the PendingIntent web page
        val pendingIntentIntent = Intent(Intent.ACTION_VIEW)
        pendingIntentIntent.data = Uri.parse(PENDING_INTENT_URL)
        val pendingIntentPendingIntent = PendingIntent.getActivity(
            context,
            PENDING_INTENT_PENDING_INTENT_ID,
            pendingIntentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        //To create a new notification, we specify both a context and the channel that was previously created
        val builder = NotificationCompat.Builder(applicationContext, MESSAGE_CHANNEL)

        //A notification at a minimum needs a small icon, title, and descriptive text.
        builder.apply {
            setSmallIcon(R.drawable.ic_launcher_foreground)
            setContentTitle("Random Int")
            setContentText("The random int generated is $data")
            setContentIntent(launchPendingIntent)
            setAutoCancel(true)

            //Each action has a title, icon, and pending intent
            addAction(
                NotificationCompat.Action.Builder(
                    R.drawable.abc_list_divider_material,
                    context.getString(R.string.work_manager_action),
                    workManagerPendingIntent
                ).build()
            )

            addAction(
                NotificationCompat.Action.Builder(
                    R.drawable.abc_cab_background_top_material,
                    context.getString(R.string.pending_intent_action),
                    pendingIntentPendingIntent
                ).build()
            )
        }
        val notificationManager = context.getSystemService(NotificationManager::class.java)
        //Each notification must have a unique ID as well
        notificationManager.notify(MESSAGE_ID, builder.build())
        return Result.success()
    }
}