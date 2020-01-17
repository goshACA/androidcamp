package com.guardian.app.repository

import com.guardian.app.Constants
import com.guardian.app.entity.ApiResponse
import com.guardian.app.entity.RecyclerArticleItem
import com.guardian.app.retrofit.ApiManager
import com.guardian.app.room.RecyclerArticleDao
import com.guardian.app.room.RecyclerArticleDatabase
import io.reactivex.Observable
import kotlinx.coroutines.*


class RecyclerArticleRepository(private val database: RecyclerArticleDatabase) {
    private val recyclerArticleDao: RecyclerArticleDao = database.recyclerArticleDao
    private val apiService = ApiManager.getGuardianRequest()

    private suspend fun getDeletedArticles(): List<RecyclerArticleItem> = GlobalScope.async {
        return@async recyclerArticleDao.getDeletedArticles()
    }.await()

 /*   private suspend fun getArticleOrderedByDate(): List<RecyclerArticleItem> = GlobalScope.async {
        return@async recyclerArticleDao.getArticlesSortedByDate()
    }.await()

    fun getArticlesSortedByDate() :List<RecyclerArticleItem> = runBlocking {
        getArticleOrderedByDate()
    }*/

    fun getDeletedArticlesFromDb(): List<RecyclerArticleItem> = runBlocking {
        getDeletedArticles()
    }

    fun getLikedArticlesFromDb(): List<RecyclerArticleItem> = runBlocking {
        getLikedArticles()
    }

    private suspend fun getSavedArticles(): List<RecyclerArticleItem> = GlobalScope.async {
        return@async recyclerArticleDao.getSavedArticles()
    }.await()

    fun getSavedArticlesFromDb() = runBlocking {
        getSavedArticles()
    }

    private suspend fun getLikedArticles(): List<RecyclerArticleItem> = GlobalScope.async {
        return@async recyclerArticleDao.getLikedArticles()
    }.await()

    private suspend fun insertArticle(article: RecyclerArticleItem) =
        withContext(Dispatchers.Default) {
            recyclerArticleDao.insertArticle(article)
        }

    suspend fun updateArticle(article: RecyclerArticleItem): Int = GlobalScope.async {
        return@async recyclerArticleDao.updateArticle(article)
    }.await()


    fun insertData(data: List<RecyclerArticleItem>?) = runBlocking {
        if (data == null) return@runBlocking
        for (article in data)
            insertArticle(article)
    }

    suspend fun deleteArticle(article: RecyclerArticleItem) =
        withContext(Dispatchers.IO) {
            article.isDeleted = true
            recyclerArticleDao.updateArticle(article)
        }


    fun getDataFromApi(pageNumber: Int): Observable<ApiResponse> = apiService.searchArticles(
        Constants.API_KEY,
        Constants.ARTICLE_TYPE,
        Constants.THUMBNAIL,
        pageNumber
    )

    fun getNewDataFromApi(fromDate: String): Observable<ApiResponse> = apiService.searchArticles(
        Constants.API_KEY,
        Constants.ARTICLE_TYPE,
        Constants.THUMBNAIL,
        1,
        fromDate
    )


}