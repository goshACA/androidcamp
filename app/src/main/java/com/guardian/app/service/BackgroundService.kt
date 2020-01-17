package com.guardian.app.service

import android.annotation.SuppressLint
import android.app.*
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import com.guardian.app.Constants
import com.guardian.app.R
import com.guardian.app.entity.ApiResponse
import com.guardian.app.repository.RecyclerArticleRepository
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class BackgroundService : LifecycleService() {

    companion object {
        lateinit var repository: RecyclerArticleRepository
    }

    override fun onCreate() {
        super.onCreate()

    }


    @SuppressLint("CheckResult")
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val currentTime = intent.getStringExtra(Constants.CURRENT_DATE)
        repository.getNewDataFromApi(currentTime!!).subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io()).subscribe(object : io.reactivex.Observer<ApiResponse> {
                override fun onComplete() {}

                override fun onNext(t: ApiResponse) {
                    val apiData = t.apiResponse.articleResult
                    apiData?.removeAll(repository.getSavedArticlesFromDb())
                    if (apiData != null && apiData.size > 0)
                        createNotification()
                }

                override fun onError(e: Throwable) {}
                override fun onSubscribe(d: Disposable) {}

            })
        super.onStartCommand(intent, flags, startId)
        // onTaskRemoved(intent)
        return Service.START_STICKY
    }

    private fun createSimpleNotification() {
        val notificationBuilder = getNotificationBuilder()
        getSystemService(
            NotificationManager::class.java
        )?.notify(0, notificationBuilder.build())
    }

    private fun getNotificationBuilder() =
        NotificationCompat.Builder(this, "theguardian")
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("TheGuardianApp")
            .setContentText("Check new articles")
            .setAutoCancel(true)
            .setColor(-0x8900)
            .setVibrate(longArrayOf(100, 100, 100, 100))
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))


    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun createChannelNotification() {
        val chanel_id = "3000"
        val name: CharSequence = "Channel"
        val description = "TheGuardianChannel"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(chanel_id, name, importance)
        channel.description = description
        channel.enableLights(true)
        channel.lightColor = Color.BLUE
        channel.importance = NotificationManager.IMPORTANCE_HIGH
        val notificationBuilder = getNotificationBuilder()
        notificationBuilder.setChannelId(chanel_id)
        val notificationManager = getSystemService(
            NotificationManager::class.java
        )
        notificationManager?.createNotificationChannel(channel)
        notificationManager?.notify(0, notificationBuilder.build())
    }


    fun createNotification() =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannelNotification();
        } else {
            createSimpleNotification();
        }


}