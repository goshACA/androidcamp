package com.guardian.app.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.os.Handler
import androidx.annotation.RequiresApi
import androidx.core.app.JobIntentService
import androidx.core.app.NotificationCompat
import com.guardian.app.Constants
import com.guardian.app.R
import com.guardian.app.entity.ApiResponse
import com.guardian.app.repository.RecyclerArticleRepository
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class NotificationJobIntentService: JobIntentService() {
    private val handler = Handler()
    companion object{
        val JOB_ID = 300
     //   lateinit var repository: RecyclerArticleRepository
        fun enqueueWork(context: Context, work: Intent){
            enqueueWork(context, NotificationJobIntentService::class.java, JOB_ID, work)
        }
    }
    override fun onHandleWork(intent: Intent) {
       /* val currentTime = intent.getStringExtra(Constants.CURRENT_DATE)
        BackgroundService.repository.getNewDataFromApi(currentTime!!).subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io()).subscribe(object : io.reactivex.Observer<ApiResponse> {
                override fun onComplete() {
                    handler.postDelayed({onHandleWork(intent)}, 2000)
                }

                override fun onNext(t: ApiResponse) {
                    val apiData = t.apiResponse.articleResult
                    apiData?.removeAll(BackgroundService.repository.getSavedArticlesFromDb())
                    if (apiData != null && apiData.size > 0)
                        createNotification()
                }

                override fun onError(e: Throwable) {}
                override fun onSubscribe(d: Disposable) {}

            })*/
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