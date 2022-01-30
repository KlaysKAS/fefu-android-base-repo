package ru.fefu.activitytracker.newActivity

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import ru.fefu.activitytracker.App
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.activities.NavigateActivity
import ru.fefu.activitytracker.db.entity.Coordinates

class NewActivityService : Service() {

    companion object {
        private const val TAG = "ForegroundService"
        private const val CHANNEL_ID = "foreground_service_id"
        private const val EXTRA_ID = "id"

        const val ACTION_START = "start"
        const val ACTION_CANCEL = "cancel"

        val locationRequest: LocationRequest
            get() = LocationRequest.create()
                .setInterval(10000L)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setSmallestDisplacement(20f)

        fun startForeground(context: Context, id: Long) {
            Log.d("track", "service start")
            val intent = Intent(context, NewActivityService::class.java)
            intent.putExtra(EXTRA_ID, id)
            intent.action = ACTION_START
            ContextCompat.startForegroundService(context, intent)
        }
    }

    private val fusedLocationClient by lazy { LocationServices.getFusedLocationProviderClient(this) }

    private var locationCallback: LocationCallback? = null

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        Log.d(TAG, "onStartCommand; ${intent?.getIntExtra(EXTRA_ID, -1)}")
        if (intent?.action == ACTION_CANCEL) {
            val actId = App.INSTANCE.db.activityDao().getLastActivity()?:-1
            if (actId > -1)
                App.INSTANCE.db.activityDao().finishActivity(System.currentTimeMillis(), actId)
            val intent2 = Intent(this, NavigateActivity::class.java)
            startActivity(intent2)
            stopLocationUpdates()
            stopForeground(true)
            stopSelf()
            return START_NOT_STICKY
        } else if (intent?.action == ACTION_START) {
            startLocationUpdates(intent.getLongExtra(EXTRA_ID, -1))
            return START_REDELIVER_INTENT
        }
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        locationCallback?.let { fusedLocationClient.removeLocationUpdates(it) }
        Log.d(TAG, "onDestroy")
        super.onDestroy()
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates(id: Long) {
        Log.d("track", "service loc update act, id: $id")
        if (id == -1L) stopSelf()
        //check if permission denied then stopSelf
        locationCallback?.let { fusedLocationClient.removeLocationUpdates(it) }
        ActivityLocationCallback(id).apply {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                this,
                Looper.getMainLooper()
            )
            locationCallback = this
        }
        showNotification()
    }

    private fun stopLocationUpdates() {
        locationCallback?.let { fusedLocationClient.removeLocationUpdates(it) }
    }

    private fun showNotification() {
        createChannelIfNeeded()
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, NewActivityActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE
        )
        val cancelIntent = Intent(this, NewActivityService::class.java).apply {
            action = ACTION_CANCEL
        }
        val cancelPendingIntent = PendingIntent.getService(
            this,
            1,
            cancelIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("ActivityTracker")
            .setContentText("Tracking your activity")
            .setSmallIcon(R.drawable.ic_user_loc)
            .setContentIntent(pendingIntent)
            .addAction(R.drawable.ic_finish, "Stop", cancelPendingIntent)
            .build()
        startForeground(1, notification)
    }

    private fun createChannelIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Default channel",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    inner class ActivityLocationCallback(private val activityId: Long) : LocationCallback() {
        override fun onLocationResult(result: LocationResult?) {
            val lastLocation = result?.lastLocation ?: return
            // put values into db
            App.INSTANCE.db.activityDao().insertCoordinates(
                Coordinates(
                    0,
                    activityId,
                    lastLocation.longitude,
                    lastLocation.latitude
                )
            )
            // https://developer.android.com/reference/androidx/room/Update
            Log.d(TAG, "Latitude: ${lastLocation.latitude}; Longitude: ${lastLocation.longitude}")
        }
    }
}