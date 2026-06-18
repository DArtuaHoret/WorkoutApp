package com.example.workoutapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.workoutapp.R



class WorkoutNotificationService : Service() {

    companion object {
        const val CHANNEL_ID = "workout_timer_channel"
        const val NOTIFICATION_ID = 1001

        const val ACTION_UPDATE = "com.example.workoutapp.ACTION_UPDATE_NOTIFICATION"
        const val EXTRA_EXERCISE_NAME = "extra_exercise_name"
        const val EXTRA_IS_RESTING = "extra_is_resting"
        const val EXTRA_SECONDS_LEFT = "extra_seconds_left"
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let { handleIntent(it) }
        return START_NOT_STICKY
    }

    private fun handleIntent(intent: Intent) {
        val exerciseName = intent.getStringExtra(EXTRA_EXERCISE_NAME) ?: ""
        val isResting = intent.getBooleanExtra(EXTRA_IS_RESTING, false)
        val secondsLeft = intent.getIntExtra(EXTRA_SECONDS_LEFT, -1)

        val notification = buildNotification(exerciseName, isResting, secondsLeft)
        val manager = getSystemService(NotificationManager::class.java)
        manager.notify(NOTIFICATION_ID, notification)
    }

    private fun buildNotification(
        exerciseName: String,
        isResting: Boolean,
        secondsLeft: Int
    ): android.app.Notification {
        val statusText = if (isResting) {
            if (secondsLeft >= 0) {
                getString(R.string.notification_rest_with_time, secondsLeft)
            } else {
                getString(R.string.notification_rest)
            }
        } else {
            getString(R.string.notification_exercising)
        }

        val title = if (exerciseName.isBlank()) {
            getString(R.string.notification_workout_in_progress)
        } else {
            exerciseName
        }

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(statusText)
            .setSmallIcon(android.R.drawable.ic_popup_reminder)
            .setOngoing(true)
            .setOnlyAlertOnce(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                getString(R.string.notification_channel_name),
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = getString(R.string.notification_channel_description)
            }
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val manager = getSystemService(NotificationManager::class.java)
        manager.cancel(NOTIFICATION_ID)
    }

    override fun onBind(intent: Intent?): IBinder? = null
}