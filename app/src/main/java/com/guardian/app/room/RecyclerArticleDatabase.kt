package com.guardian.app.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.guardian.app.entity.RecyclerArticleItem
import org.koin.core.KoinComponent

@Database(entities = [RecyclerArticleItem::class], version = 1, exportSchema = false)
abstract class RecyclerArticleDatabase: RoomDatabase() {
    abstract val recyclerArticleDao: RecyclerArticleDao



}