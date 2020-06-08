package com.trial.listsectionssample.core.di

import android.content.Context
import com.google.gson.Gson
import com.trial.listsectionssample.BuildConfig
import com.trial.listsectionssample.core.data.remote.RemoteDataSource
import com.trial.listsectionssample.core.data.remote.RemoteDataSourceImpl
import com.trial.listsectionssample.core.networkError.RxErrorHandlingCallAdapterFactory
import com.trial.listsectionssample.core.utils.ConnectivityUtils
import com.trial.listsectionssample.core.utils.IConnectivityUtils
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class, RepositoriesModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(
        adapterFactory: RxErrorHandlingCallAdapterFactory,
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(adapterFactory)
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttp(context: Context): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG)
            logging.level = HttpLoggingInterceptor.Level.BODY
        else logging.level = HttpLoggingInterceptor.Level.NONE
        return OkHttpClient.Builder()
            .readTimeout(1, TimeUnit.MINUTES)
            .connectTimeout(1, TimeUnit.MINUTES)
            .addInterceptor(logging)
            .build()
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return Gson()
            .newBuilder()
            .setDateFormat("yyyy/MM/dd")
            .create()
    }

    @Singleton
    @Provides
    fun provideRemoteDataSource(retrofit: Retrofit): RemoteDataSource {
        return RemoteDataSourceImpl(retrofit)
    }

    @Singleton
    @Provides
    fun provideRxErrorHandlingCallAdapterFactory(): RxErrorHandlingCallAdapterFactory {
        return RxErrorHandlingCallAdapterFactory.create()
    }


    @Singleton
    @Provides
    fun provideConnectivityUtils(context: Context): IConnectivityUtils = ConnectivityUtils(context)


}