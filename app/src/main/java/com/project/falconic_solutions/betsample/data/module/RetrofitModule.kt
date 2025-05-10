package com.project.falconic_solutions.betsample.data.module

import com.project.falconic_solutions.betsample.BuildConfig
import com.project.falconic_solutions.betsample.data.service.ApiService
import com.project.falconic_solutions.betsample.data.service.impl.ApiServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val client = OkHttpClient.Builder().also { client ->
            client.connectTimeout(10, TimeUnit.SECONDS)
            client.readTimeout(60, TimeUnit.SECONDS)
            client.writeTimeout(60, TimeUnit.SECONDS)
            if (BuildConfig.DEBUG) {
                val loggingInterceptor = HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
                client.addInterceptor(loggingInterceptor)
            }
        }.build()
        return Retrofit.Builder()
            .baseUrl("https://api.the-odds-api.com/v4/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideApiServiceImpl(apiService: ApiService): ApiServiceImpl {
        return ApiServiceImpl(apiService)
    }
}