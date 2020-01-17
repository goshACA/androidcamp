package com.guardian.app.viewmodel.usecase

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.guardian.app.entity.ApiResponse
import com.guardian.app.entity.ArticleItem
import com.guardian.app.repository.ArticleRepository
import com.guardian.app.utils.NetworkConnectionUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class GetArticleItemDataUseCase(private val repository: ArticleRepository) {

    fun getArticle(
        liveData: MutableLiveData<ArticleItem>,
        apiKey: String,
        fields: String,
        articleId: String,
        context: Context
    ) {
        if (NetworkConnectionUtils.isNetworkAvailable(context)) {
            getArticleFromApi(liveData, apiKey, fields, articleId)
        } else {
            getArticleFromDb(articleId)
        }
    }

    @SuppressLint("CheckResult")
    private fun getArticleFromApi(
        liveData: MutableLiveData<ArticleItem>,
        apiKey: String,
        fields: String,
        id: String
    ) {
        repository.getArticleFromApi(apiKey, fields, id).subscribeOn(Schedulers.io()).observeOn(
            AndroidSchedulers.mainThread()
        )
            .subscribe(object : io.reactivex.Observer<ApiResponse> {
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: ApiResponse) {
                    val article = t.apiResponse.articleResult?.get(0)
                    if (article != null) {
                        liveData.value = article
                        repository.insertArticleInDb(article)
                    }
                }

                override fun onError(e: Throwable) {}

            })
    }

    private fun getArticleFromDb(articleId: String): ArticleItem =
        repository.getArticleByIdFromDb(articleId)


}