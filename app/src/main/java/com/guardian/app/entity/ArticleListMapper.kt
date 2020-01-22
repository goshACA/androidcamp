package com.guardian.app.entity

import com.google.gson.annotations.SerializedName

class ArticleListMapper {
    @SerializedName("currentPage")
    var pageNumber: Int = 1
    @SerializedName("results")
    var articleResult: ArrayList<RecyclerArticleItem>? = null
}