package com.builditcreative.qfonapp.data

import android.content.Context
import androidx.room.Room
import com.builditcreative.qfonapp.data.local.AppDatabase
import com.builditcreative.qfonapp.data.local.PassengerDao
import com.builditcreative.qfonapp.data.remote.AppApi
import com.builditcreative.qfonapp.data.remote.RemoteRepository
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiClient {

    @Provides
    @Singleton
    fun getRetrofitApi(okHttpClient: OkHttpClient): AppApi {
        return Retrofit.Builder()
            .baseUrl("BASE_URL")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
            .create(AppApi::class.java)
    }

    @Provides
    @Singleton
    fun getRepository(api: AppApi): RemoteRepository {
        return RemoteRepository(api)
    }

    @Provides
    @Singleton
    fun getAppRepository(remoteRepository: RemoteRepository,passengerDao: PassengerDao,isNetworkAvailable:Boolean): AppRepository {
        return AppRepository(remoteRepository,passengerDao, isNetworkAvailable)
    }

    @Singleton
    @Provides
    fun createDefaultOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient().newBuilder()
            .connectTimeout(1000, TimeUnit.SECONDS)
            .readTimeout(1000, TimeUnit.SECONDS)
            .writeTimeout(1000, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "qfon.db"
        ).allowMainThreadQueries().build()
    }

    @Singleton
    @Provides
    fun providePassengerDao(appDatabase: AppDatabase): PassengerDao {
        return appDatabase.passengerDao()
    }

    @Singleton
    @Provides
    fun isNetworkAvailable(@ApplicationContext context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as android.net.ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

}