package com.guardian.app.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "recycler_articles")
class RecyclerArticleItem : ArticleItem() {
    override fun equals(other: Any?) = other is RecyclerArticleItem
            && other.id == id

    @ColumnInfo(name = "isDeleted")
    var isDeleted: Boolean = false
    @ColumnInfo(name = "isLiked")
    var isLiked: Boolean = false


}