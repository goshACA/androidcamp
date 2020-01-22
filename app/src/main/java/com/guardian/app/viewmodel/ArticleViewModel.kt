package com.guardian.app.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.guardian.app.Constants
import com.guardian.app.entity.ArticleItem
import com.guardian.app.repository.ArticleRepository
import com.guardian.app.room.ArticleDatabase
import com.guardian.app.room.RecyclerArticleDatabase
import com.guardian.app.viewmodel.usecase.GetArticleItemDataUseCase
import org.koin.core.KoinComponent
import org.koin.core.parameter.parametersOf

class ArticleViewModel(private val getArticleItemUseCase: GetArticleItemDataUseCase) : ViewModel() {
    val articleLiveData: MutableLiveData<ArticleItem> = MutableLiveData()

    fun requestArticle(articleId: String, context: Context)  = getArticleItemUseCase.getArticle(articleLiveData,  Constants.API_KEY,
        Constants.THUMBNAIL + ',' + Constants.BODY_TEXT, articleId, context)

}
