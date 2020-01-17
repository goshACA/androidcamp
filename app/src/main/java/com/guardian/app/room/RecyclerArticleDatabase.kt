package com.guardian.app.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.guardian.app.entity.RecyclerArticleItem

@Database(entities = [RecyclerArticleItem::class], version = 1, exportSchema = false)
abstract class RecyclerArticleDatabase: RoomDatabase() {
    abstract val recyclerArticleDao: RecyclerArticleDao

    companion object {
        @Volatile
        private var INSTANCE: RecyclerArticleDatabase? = null

        fun getInstance(context: Context): RecyclerArticleDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        RecyclerArticleDatabase::class.java,
                        "recycler_articles"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}