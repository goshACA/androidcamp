package com.guardian.app.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.guardian.app.Constants
import com.guardian.app.entity.ArticleItem
import com.guardian.app.repository.ArticleRepository
import com.guardian.app.room.ArticleDatabase
import com.guardian.app.viewmodel.usecase.GetArticleItemDataUseCase

class ArticleViewModel(application: Application) : ViewModel() {
    val articleLiveData: MutableLiveData<ArticleItem> = MutableLiveData()

    private val getArticleItemUseCase = GetArticleItemDataUseCase(ArticleRepository(ArticleDatabase.getInstance(application)))

    fun requestArticle(articleId: String, context: Context)  = getArticleItemUseCase.getArticle(articleLiveData,  Constants.API_KEY,
        Constants.THUMBNAIL + ',' + Constants.BODY_TEXT, articleId, context)

}
