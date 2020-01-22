package com.guardian.app.room

import androidx.room.*
import com.guardian.app.entity.RecyclerArticleItem

@Dao
interface RecyclerArticleDao {

  /*  @Query("SELECT * FROM recycler_articles ORDER BY date")
    fun getArticlesSortedByDate(): List<RecyclerArticleItem>*/

    @Query("SELECT * FROM recycler_articles WHERE isDeleted = 1")
    fun getDeletedArticles(): List<RecyclerArticleItem>

    @Query("SELECT * FROM recycler_articles WHERE isDeleted = 0")
    fun getSavedArticles(): List<RecyclerArticleItem>

    @Query("SELECT * FROM recycler_articles WHERE isLiked = 1")
    fun getLikedArticles(): List<RecyclerArticleItem>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertArticle(article: RecyclerArticleItem)

    @Update
    fun updateArticle(article: RecyclerArticleItem): Int

}