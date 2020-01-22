package com.guardian.app.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.guardian.app.Constants
import com.guardian.app.entity.RecyclerArticleItem
import com.guardian.app.repository.ArticleRepository
import com.guardian.app.repository.RecyclerArticleRepository
import com.guardian.app.room.RecyclerArticleDatabase
import com.guardian.app.utils.LocalDateUtils
import com.guardian.app.view.fragment.NewsFragment
import com.guardian.app.viewmodel.usecase.GetRecyclerDataUseCase
import com.guardian.app.viewmodel.usecase.UpdateRecyclerItemUseCase
import org.koin.core.KoinComponent
import org.koin.core.inject


class RecyclerViewModel(application: Application, private val dataUseCase:
    GetRecyclerDataUseCase, private val updateItemUseCase:
    UpdateRecyclerItemUseCase) : ViewModel() {
    val articleListLiveData: MutableLiveData<ArrayList<RecyclerArticleItem>> = MutableLiveData()
    private val sharedPreferenceBooleanLiveData: SharedPreferenceBooleanLiveData =
        SharedPreferenceBooleanLiveData(
            application.getSharedPreferences(Constants.BOOLEAN_PREF, Context.MODE_PRIVATE),
            Constants.DESIGN_IS_PINTEREST_KEY
        )
    private val sharedPreferenceIntLiveData: SharedPreferenceIntLiveData =
        SharedPreferenceIntLiveData(
            application.getSharedPreferences(Constants.POS_SHARED_PREF, Context.MODE_PRIVATE),
            Constants.POS_SHARED_PREF_KEY
        )




    fun saveDesignState(isPinterest: Boolean) {
        sharedPreferenceBooleanLiveData.putValueInPreferences(
            Constants.DESIGN_IS_PINTEREST_KEY,
            isPinterest
        )
    }

    fun saveIsClicked(isClicked: Boolean) =
        sharedPreferenceBooleanLiveData.putValueInPreferences(Constants.CLICKED_PREF_KEY, isClicked)

    fun getDesignState(): Boolean =
        sharedPreferenceBooleanLiveData.getValueFromPreferences(Constants.DESIGN_IS_PINTEREST_KEY)

    fun getIsClicked(): Boolean =
        sharedPreferenceBooleanLiveData.getValueFromPreferences(Constants.CLICKED_PREF_KEY)

    fun getRecyclerPosition(): Int =
        sharedPreferenceIntLiveData.getValueFromPreferences(Constants.POS_SHARED_PREF_KEY)

    fun saveRecyclerPosition(pos: Int) =
        sharedPreferenceIntLiveData.putValueInPreferences(Constants.POS_SHARED_PREF_KEY, pos)

    fun getArticlesData(
        context: Context,
        successListener: NewsFragment.OnSuccessListener? = null,
        pageNumber: Int = 1
    ) = dataUseCase.getData(articleListLiveData, successListener, pageNumber, context)

    fun loadNewData(fromDate: String, context: Context) {
//        dataUseCase.getNewData(articleListLiveData, fromDate, context)
    }

    fun likeItem(item: RecyclerArticleItem) = updateItemUseCase.likeItem(item)

    fun removeLikeFromItem(item: RecyclerArticleItem) = updateItemUseCase.removeLikeFromItem(item)

    fun deleteItem(item: RecyclerArticleItem) = updateItemUseCase.deleteItem(item)

    fun restoreItem(item: RecyclerArticleItem) = updateItemUseCase.restoreItem(item)

    fun startService(intent: Intent, context: Context)  {
        intent.putExtra(Constants.CURRENT_DATE, LocalDateUtils.getCurrentDateString())
        dataUseCase.startService(intent, context)
    }



}