package ru.fefu.activitytracker.newActivity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import ru.fefu.activitytracker.R

class NewActivityService: Service() {
    companion object {
        private const val TAG = "New activity service"
        private const val CHANNEL_ID = "new_activity_service_id"
        private const val EXTRA_ID = "id"

        private const val ACTION_CANCEL = "cancel"
        fun startForeground(context: Context, id: Int) {
            val intent = Intent(context, NewActivityService::class.java)
            intent.putExtra(EXTRA_ID, id)
            ContextCompat.startForegroundService(context, intent)
        }
    }
    private var id: Int = -1

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        id = intent?.getIntExtra(EXTRA_ID, -1)?:-1
        if (intent?.action == ACTION_CANCEL) {
            stopForeground(true)
            stopSelf()
            return START_NOT_STICKY
        }
        showNotification()
        super.onStartCommand(intent, flags, startId)
        return START_REDELIVER_INTENT
    }

    private fun showNotification() {
        createChannelIfNeeded()
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, NewActivityActivity::class.java),
            0
        )
        val cancelIntent = Intent(this, NewActivityService::class.java).apply {
            action = ACTION_CANCEL
        }
        val cancelPendingIntent = PendingIntent.getService(
            this,
            1,
            cancelIntent,
            0
        )
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Hello")
            .setContentText("Tracking your activity")
            .setContentIntent(pendingIntent)
            .addAction(R.drawable.ic_finish, "Stop", cancelPendingIntent)
            .build()
        startForeground(1, notification)
    }

    private fun createChannelIfNeeded() {
        val channel = NotificationChannel(CHANNEL_ID, "Default channel", NotificationManager.IMPORTANCE_LOW)
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }
}