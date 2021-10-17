package com.guardian.app.retrofit

import com.guardian.app.entity.ApiResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleRequest {
    @GET("search")
    fun searchArticle(
        @Query("api-key") apiKey: String, @Query("show-fields") fields: String, @Query(
            "ids"
        ) id: String
    ): Observable<ApiResponse>
}