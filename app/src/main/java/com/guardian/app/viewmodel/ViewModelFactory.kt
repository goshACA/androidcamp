package com.guardian.app.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class ViewModelFactory(private val mApplication: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass == ArticleViewModel::class.java)
            return ArticleViewModel(mApplication) as T
        else return RecyclerViewModel(mApplication) as T
    }

}