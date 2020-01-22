package com.guardian.app

import android.app.Application
import com.guardian.app.koin.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class GuardianApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@GuardianApplication)
            modules(appModule)
        }


    }
}