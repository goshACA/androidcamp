package com.guardian.app.utils

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager


class NetworkConnectionUtils {
    companion object {
        fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetwork
            return networkInfo != null
        }
    }
}