package com.guardian.app.retrofit

import com.guardian.app.entity.ApiResponse
import io.reactivex.Observable

abstract class GuardianApiResolver: GuardianRequest{
   abstract override fun searchArticles(
        apiKey: String,
        articleType: String,
        field: String,
        page: Int
    ): Observable<ApiResponse>

    abstract override fun searchArticles(
        apiKey: String,
        articleType: String,
        field: String,
        page: Int,
        fromDate: String
    ): Observable<ApiResponse>

}