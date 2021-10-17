package com.guardian.app.paging.datasource

import android.provider.SyncStateContract
import android.telecom.Call
import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.guardian.app.Constants
import com.guardian.app.entity.ApiResponse
import com.guardian.app.entity.RecyclerArticleItem
import com.guardian.app.retrofit.GuardianRequest
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.Callback
import retrofit2.Response

class PageDataSource : PageKeyedDataSource<Long, RecyclerArticleItem>(), KoinComponent {

    val guardianRequest by inject<GuardianRequest>()
    override fun loadInitial(
        params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<Long, RecyclerArticleItem>
    ) {
        guardianRequest.searchExampleArticles(
            Constants.API_KEY,
            Constants.ARTICLE_TYPE,
            Constants.THUMBNAIL,
            1
        ).enqueue(object : Callback<ApiResponse> {
            override fun onFailure(call: retrofit2.Call<ApiResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: retrofit2.Call<ApiResponse>,
                response: Response<ApiResponse>
            ) {
                val result = response.body()?.apiResponse?.articleResult
                if (response.isSuccessful && result != null) {
                   callback.onResult(result, null, 2)
                } else {
                    Log.d("zibil", "zibil")
                }
            }

        })


    }

    override fun loadAfter(
        params: LoadParams<Long>,
        callback: LoadCallback<Long, RecyclerArticleItem>
    ) {
        guardianRequest.searchExampleArticles(
            Constants.API_KEY,
            Constants.ARTICLE_TYPE,
            Constants.THUMBNAIL,
            params.key.toInt()
        ).enqueue(object : Callback<ApiResponse> {
            override fun onFailure(call: retrofit2.Call<ApiResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: retrofit2.Call<ApiResponse>,
                response: Response<ApiResponse>
            ) {
                val result = response.body()?.apiResponse?.articleResult
                if (response.isSuccessful && result != null) {
                    val nextKey = params.key + 1
                    callback.onResult(result, nextKey as Long)
                } else {
                    Log.d("zibil", "zibil")
                }
            }

        })
    }

    override fun loadBefore(
        params: LoadParams<Long>,
        callback: LoadCallback<Long, RecyclerArticleItem>
    ) {
    }

}