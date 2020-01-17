package com.guardian.app.viewmodel.usecase

import com.guardian.app.entity.RecyclerArticleItem
import com.guardian.app.repository.RecyclerArticleRepository
import kotlinx.coroutines.runBlocking

class UpdateRecyclerItemUseCase(private val repository: RecyclerArticleRepository) {
    fun likeItem(item: RecyclerArticleItem) = runBlocking {
        item.isLiked = true
        repository.updateArticle(item)
    }

    fun removeLikeFromItem(item: RecyclerArticleItem) = runBlocking {
        item.isLiked = false
        repository.updateArticle(item)
    }

    fun deleteItem(item: RecyclerArticleItem) = runBlocking {
        item.isDeleted = true
        repository.updateArticle(item)
    }


    fun restoreItem(item: RecyclerArticleItem) = runBlocking {
        item.isDeleted = false
        repository.updateArticle(item)
    }

}