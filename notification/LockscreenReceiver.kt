package com.example.jetpack.notification

import android.Manifest
import android.app.KeyguardManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.PowerManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.jetpack.MainActivity
import com.example.jetpack.R
import com.example.jetpack.configuration.Constant.DEFAULT_LOCKSCREEN_ID
import com.example.jetpack.configuration.Constant.LOCKSCREEN_CHANNEL_ID
import com.example.jetpack.ui.activity.LockscreenActivity
import com.example.jetpack.util.AppUtil


class LockscreenReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        /*0. Start repeating notification if the device was shut down and then reboot*/
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            LockscreenManager.setupDailyLockscreenNotification(context)
        }


        AppUtil.logcat("BroadcastReceiver: Send lockscreen-styled notification !", tag = "Notification")

        val powerManager = context.getSystemService(PowerManager::class.java)
        val keyguardManager = context.getSystemService(KeyguardManager::class.java)

        if (!powerManager.isInteractive || keyguardManager.isKeyguardLocked) {
            AppUtil.logcat("BroadcastReceiver: popupLockscreenNotification !", tag = "Notification")
            popupLockscreenNotification(context)
        } else {
            AppUtil.logcat("BroadcastReceiver: popupNormalNotification !", tag = "Notification")
            popupNormalNotification(context)
        }


        //4. Set again this alarm manager
        LockscreenManager.setupDailyLockscreenNotification(context)
    }

    /**
     * send a lockscreen-styled notification
     */
    private fun popupLockscreenNotification(context: Context) {
        //1. Create pending
        val lockscreenIntent = Intent(context, LockscreenActivity::class.java)
        lockscreenIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val lockscreenPendingIntent = PendingIntent.getActivity(context, 0, lockscreenIntent, PendingIntent.FLAG_IMMUTABLE)


        //2. Setup notification builder
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(context, LOCKSCREEN_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_nazi_symbol)
                .setStyle(NotificationCompat.DecoratedCustomViewStyle())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_CALL)
                .setFullScreenIntent(lockscreenPendingIntent, true)


        //3. Show notification with notificationId which is a unique int for each notification that you must define
        val notificationManager = NotificationManagerCompat.from(context)
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        notificationManager.cancel(DEFAULT_LOCKSCREEN_ID)
        notificationManager.notify(DEFAULT_LOCKSCREEN_ID, builder.build())
    }


    /**
     * send a normal notification instead of lockscreen-styled notification
     * */
    private fun popupNormalNotification(context: Context) {
        //2. Create an explicit intent for an Activity in your app
        val destinationIntent = Intent(context, MainActivity::class.java)
        destinationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        val pendingIntent = PendingIntent.getActivity(
            context,
            1896,
            destinationIntent,
            PendingIntent.FLAG_IMMUTABLE
        )


        //3. define notification builder
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(context, LOCKSCREEN_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_nazi_symbol)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(context.getString(R.string.fake_title))
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(context.getString(R.string.fake_message))
                )
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)


        //4. Show notification with notificationId which is a unique int for each notification that you must define
        val notificationManager = NotificationManagerCompat.from(context)
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        notificationManager.notify(DEFAULT_LOCKSCREEN_ID, builder.build())
    }
}