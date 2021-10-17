package com.guardian.app.room

import android.content.Context
import androidx.room.*
import com.guardian.app.entity.ArticleItem

@Database(entities = [ArticleItem::class], version = 1, exportSchema = false)
abstract class ArticleDatabase : RoomDatabase() {
    abstract val articleDao: ArticleDao
}