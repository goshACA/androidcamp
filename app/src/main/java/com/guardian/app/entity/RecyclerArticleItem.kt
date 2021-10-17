package com.guardian.app.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import android.R.id
import androidx.annotation.NonNull
import androidx.recyclerview.widget.DiffUtil


@Entity(tableName = "recycler_articles")
class RecyclerArticleItem : ArticleItem() {
    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<RecyclerArticleItem> =
            object : DiffUtil.ItemCallback<RecyclerArticleItem>() {
                override fun areItemsTheSame(@NonNull oldItem: RecyclerArticleItem, @NonNull newItem: RecyclerArticleItem): Boolean {
                    return oldItem.id === newItem.id
                }

                override fun areContentsTheSame(@NonNull oldItem: RecyclerArticleItem, @NonNull newItem: RecyclerArticleItem): Boolean {
                    return oldItem.equals(newItem)
                }
            }
    }

    override fun equals(other: Any?) = other is RecyclerArticleItem
            && other.id == id

    @ColumnInfo(name = "isDeleted")
    var isDeleted: Boolean = false
    @ColumnInfo(name = "isLiked")
    var isLiked: Boolean = false


}