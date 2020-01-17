package com.guardian.app.retrofit

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiManager {
    companion object {
        private var apiClient: GuardianRequest? = null
        private var articleClient: ArticleRequest? = null
        private val BASE_URL: String = "https://content.guardianapis.com/"
        fun getGuardianRequest(): GuardianRequest {
            if (apiClient == null) {
                apiClient = getRetrofit().create(GuardianRequest::class.java)
            }
            return apiClient!!
        }

        fun getArticleRequest(): ArticleRequest{
            if (articleClient == null) {
                articleClient = getRetrofit().create(ArticleRequest::class.java)
            }
            return articleClient!!
        }


        private fun getRetrofit(): Retrofit{
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(logging)
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .build()
        }
    }
}