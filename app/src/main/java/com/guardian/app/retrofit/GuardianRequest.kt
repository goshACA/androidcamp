package com.guardian.app.retrofit

import com.guardian.app.entity.ApiResponse
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface GuardianRequest {
    @GET("search")
    fun searchArticles(
        @Query("api-key") apiKey: String, @Query("show-tags") articleType: String,
        @Query("show-fields") field: String,
        @Query("page") page: Int
    ): Observable<ApiResponse>

    @GET("search")
    fun searchArticles(
        @Query("api-key") apiKey: String, @Query("show-tags") articleType: String,
        @Query("show-fields") field: String,
        @Query("page") page: Int, @Query("from-date") fromDate: String
    ): Observable<ApiResponse>

    @GET("search")
    fun searchExampleArticles(
        @Query("api-key") apiKey: String, @Query("show-tags") articleType: String,
        @Query("show-fields") field: String,
        @Query("page") page: Int
    ): Call<ApiResponse>


}