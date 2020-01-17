package com.guardian.app.repository

import com.guardian.app.entity.ApiResponse
import com.guardian.app.entity.ArticleItem
import com.guardian.app.retrofit.ApiManager
import com.guardian.app.room.ArticleDao
import com.guardian.app.room.ArticleDatabase
import io.reactivex.Observable
import kotlinx.coroutines.*

class ArticleRepository(database: ArticleDatabase) {
    private val articleDao: ArticleDao = database.articleDao
    private val articleApi = ApiManager.getArticleRequest()

    private suspend fun getArticleById(articleId: String): ArticleItem = GlobalScope.async {
        return@async articleDao.getArticleById(articleId)
    }.await()

    fun getArticleByIdFromDb(articleId: String): ArticleItem = runBlocking {
        getArticleById(articleId)
    }

    private suspend fun insertArticle(article: ArticleItem) =
        withContext(Dispatchers.IO) {
            articleDao.insertArticle(article)
        }

    fun insertArticleInDb(article: ArticleItem) = runBlocking {
        insertArticle(article)
    }


    fun getArticleFromApi(apiString: String, fields: String, id: String): Observable<ApiResponse> =
        articleApi.searchArticle(apiString, fields, id)
}