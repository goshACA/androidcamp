package com.guardian.app.paging

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.guardian.app.entity.RecyclerArticleItem
import com.guardian.app.paging.datasource.PageDataSource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch



class DataSourceFactory: DataSource.Factory<Long, RecyclerArticleItem> (){
    private val pageDataSource = PageDataSource()
     val mutablePageSDataourceLiveData = MutableLiveData<PageDataSource>()
    override fun create(): DataSource<Long, RecyclerArticleItem> {
        Handler().post {
            mutablePageSDataourceLiveData.value = pageDataSource
        }

        return pageDataSource
    }

}