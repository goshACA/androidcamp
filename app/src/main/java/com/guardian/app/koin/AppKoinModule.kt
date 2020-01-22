package com.guardian.app.koin

import androidx.room.Room
import com.guardian.app.Constants
import com.guardian.app.repository.ArticleRepository
import com.guardian.app.repository.RecyclerArticleRepository
import com.guardian.app.retrofit.ArticleRequest
import com.guardian.app.retrofit.GuardianApiResolver
import com.guardian.app.retrofit.GuardianRequest
import com.guardian.app.retrofit.RequestBuilder
import com.guardian.app.room.ArticleDatabase
import com.guardian.app.room.RecyclerArticleDatabase
import com.guardian.app.viewmodel.ArticleViewModel
import com.guardian.app.viewmodel.RecyclerViewModel
import com.guardian.app.viewmodel.usecase.GetArticleItemDataUseCase
import com.guardian.app.viewmodel.usecase.GetRecyclerDataUseCase
import com.guardian.app.viewmodel.usecase.UpdateRecyclerItemUseCase
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    single<OkHttpClient> {
        OkHttpClient.Builder().build()
    }

    single<Retrofit> {
        provideRetrofit(get())
    }

    single(named("RETROFIT")) {
        //        val logging = HttpLoggingInterceptor()
//        logging.level = HttpLoggingInterceptor.Level.BODY
//        val httpClient = OkHttpClient.Builder()
//        httpClient.addInterceptor(logging)
//        Retrofit.Builder()
//            .baseUrl(Constants.BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .client(httpClient.build())
//            .build()

        provideRetrofit(get())
    }

    single<GuardianRequest> {
        get<Retrofit>().create(GuardianRequest::class.java)
    }

    single {
        Room.databaseBuilder(
            androidApplication(),
            RecyclerArticleDatabase::class.java,
            "recycler"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    single {
        Room.databaseBuilder(
            androidApplication(),
            ArticleDatabase::class.java,
            "article"
        )
            .fallbackToDestructiveMigration()
            .build()
    }


    factory {
        ArticleRepository(
            get<ArticleDatabase>().articleDao
        )
    }

    factory {
        RecyclerArticleRepository(get<RecyclerArticleDatabase>().recyclerArticleDao)
    }

    factory {
        UpdateRecyclerItemUseCase(get())
    }

    factory {
        GetArticleItemDataUseCase(get())
    }

    factory {
        GetRecyclerDataUseCase(get())
    }


    viewModel {
        RecyclerViewModel(androidApplication(), get(), get())
    }
    viewModel {
        ArticleViewModel(get())
    }
}

//    single {
//        GuardianApplication()
//    }


//    single(named("APISERVICE2")) {
//        get<Retrofit>().create(ArticleRequest::class.java)
//    }
//   /* single<ArticleRequest> {
//        get<Retrofit>().create(ArticleRequest::class.java)
//    }*/


/*  single { (dbClass: Class<RoomDatabase>, dbName: String) ->
      Room.databaseBuilder(
          get<GuardianApplication>(),
          dbClass,
          dbName
      )
          .fallbackToDestructiveMigration()
          .build()
  }*/





fun provideRetrofit(client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
}




