package com.guardian.app.entity

import com.google.gson.annotations.SerializedName

class ApiResponse {
    @SerializedName("response")
    var apiResponse: ArticleListMapper = ArticleListMapper()
}