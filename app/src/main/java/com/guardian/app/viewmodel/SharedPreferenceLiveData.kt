package com.guardian.app.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.LiveData

abstract class SharedPreferenceLiveData<T>(
    val sharedPrefs: SharedPreferences,
    val key: String,
    val defValue: T
) : LiveData<T>() {

    private val preferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
            if (key == this.key) {
                value = getValueFromPreferences(key)
            }
        }

    abstract fun getValueFromPreferences(key: String): T
    abstract fun putValueInPreferences(key: String, value: T)

    override fun onActive() {
        super.onActive()
        value = getValueFromPreferences(key)
        sharedPrefs.registerOnSharedPreferenceChangeListener(preferenceChangeListener)
    }

    override fun onInactive() {
        sharedPrefs.unregisterOnSharedPreferenceChangeListener(preferenceChangeListener)
        super.onInactive()
    }
}

class SharedPreferenceIntLiveData(sharedPrefs: SharedPreferences, key: String) :
    SharedPreferenceLiveData<Int>(sharedPrefs, key, 0) {
    override fun getValueFromPreferences(key: String): Int =
        sharedPrefs.getInt(key, defValue)

    override fun putValueInPreferences(key: String, value: Int) {
        sharedPrefs.edit().putInt(key, value).apply()
    }
}


class SharedPreferenceBooleanLiveData(sharedPrefs: SharedPreferences, key: String) :
    SharedPreferenceLiveData<Boolean>(sharedPrefs, key, false) {
    override fun getValueFromPreferences(key: String): Boolean =
        sharedPrefs.getBoolean(key, defValue)

    override fun putValueInPreferences(key: String, value: Boolean) {
        sharedPrefs.edit().putBoolean(key, value).apply()
    }
}

