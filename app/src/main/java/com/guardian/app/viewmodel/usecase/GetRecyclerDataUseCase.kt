package com.guardian.app.viewmodel.usecase

import android.content.Context
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import com.guardian.app.entity.ApiResponse
import com.guardian.app.entity.RecyclerArticleItem
import com.guardian.app.repository.RecyclerArticleRepository
import com.guardian.app.service.BackgroundService
import com.guardian.app.service.NotificationJobIntentService
import com.guardian.app.utils.NetworkConnectionUtils
import com.guardian.app.view.fragment.NewsFragment
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class GetRecyclerDataUseCase(private val repository: RecyclerArticleRepository) {

    fun getData(
        articleListLiveData: MutableLiveData<ArrayList<RecyclerArticleItem>>,
        successListener: NewsFragment.OnSuccessListener?, pageNumber: Int, context: Context
    ) {
        if (NetworkConnectionUtils.isNetworkAvailable(context)) {
            getDataWithNetwork(articleListLiveData, successListener, pageNumber)
        } else {
            getDataWithoutNetwork(articleListLiveData, successListener)
        }
    }

    fun getNewData(
        articleListLiveData: MutableLiveData<ArrayList<RecyclerArticleItem>>,
        fromData: String, context: Context
    ) {
        if (NetworkConnectionUtils.isNetworkAvailable(context)) {
            getNewDataFromApi(articleListLiveData, fromData)
        }
    }

    private fun getDataWithoutNetwork(
        articleListLiveData: MutableLiveData<ArrayList<RecyclerArticleItem>>,
        successListener: NewsFragment.OnSuccessListener?
    ) {
        articleListLiveData.value =
            repository.getSavedArticlesFromDb() as ArrayList<RecyclerArticleItem>
        successListener?.onSuccess(articleListLiveData.value ?: ArrayList())
    }

    private fun getDataWithNetwork(
        articleListLiveData: MutableLiveData<ArrayList<RecyclerArticleItem>>,
        successListener: NewsFragment.OnSuccessListener?,
        pageNumber: Int
    ) {
        repository.getDataFromApi(pageNumber).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ApiResponse> {
                override fun onComplete() {
                    if (articleListLiveData.value != null) {
                        successListener?.onSuccess(articleListLiveData.value!!)
                        repository.insertData(articleListLiveData.value)
                    }
                }

                override fun onNext(t: ApiResponse) {
                    setDataToLiveData(articleListLiveData, t.apiResponse.articleResult, false)
                }

                override fun onError(e: Throwable) {
                }

                override fun onSubscribe(d: Disposable) {}

            })
    }

    private fun setDataToLiveData(
        articleListLiveData: MutableLiveData<ArrayList<RecyclerArticleItem>>,
        apiData: ArrayList<RecyclerArticleItem>?,
        prefix: Boolean
    ) {
        var data: ArrayList<RecyclerArticleItem>? = null
        if (articleListLiveData.value != null && articleListLiveData.value!!.isNotEmpty()) {
            if (apiData == null) {
                return
            }
            if (prefix)
                articleListLiveData.value?.addAll(0, apiData)
            else articleListLiveData.value?.addAll(apiData)

            data = articleListLiveData.value
        } else {
            if (apiData != null)
                data = apiData
        }
        data?.removeAll(repository.getDeletedArticlesFromDb())
        val likedData = repository.getLikedArticlesFromDb()
        data?.forEach {
            if (likedData.contains(it))
                it.isLiked = true
        }

        articleListLiveData.value = data

    }

    private fun getNewDataFromApi(
        articleListLiveData: MutableLiveData<ArrayList<RecyclerArticleItem>>,
        fromData: String
    ) {
        repository.getNewDataFromApi(fromData).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : io.reactivex.Observer<ApiResponse> {
                override fun onComplete() {
                    if (articleListLiveData.value != null) {
                        repository.insertData(articleListLiveData.value)
                    }
                }

                override fun onNext(t: ApiResponse) {
                    setDataToLiveData(articleListLiveData, t.apiResponse.articleResult, true)
                }

                override fun onError(e: Throwable) {}
                override fun onSubscribe(d: Disposable) {}

            })
    }

    fun startService(intent: Intent, context: Context){
        NotificationJobIntentService.repository = repository
        NotificationJobIntentService.enqueueWork(context, intent)
        context.startService(intent)
        BackgroundService.repository = repository
    }
}