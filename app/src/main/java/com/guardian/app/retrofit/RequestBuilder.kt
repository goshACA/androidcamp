package com.guardian.app.retrofit

import retrofit2.Retrofit

class RequestBuilder(private val retrofit: Retrofit) {
    fun guardianRequestInstance(): GuardianRequest {
        return retrofit.create(GuardianRequest::class.java)
    }

    fun articleRequestInstance(): ArticleRequest {
        return retrofit.create(ArticleRequest::class.java)
    }
}