package com.guardian.app.room

import androidx.room.*
import com.guardian.app.entity.ArticleItem

@Dao
interface ArticleDao {
    @Query("SELECT * FROM clicked_articles WHERE id=:id")
    fun getArticleById(id: String): ArticleItem

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticle(article: ArticleItem)


}
