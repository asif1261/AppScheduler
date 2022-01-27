package com.example.appscheduler.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /*@Singleton
    @Provides
    fun provideLoginApi(
        @ApplicationContext context: Context,
        retrofitBuilder: RetrofitBuilder
    ): LoginApi {
        return retrofitBuilder.buildApi(LoginApi::class.java, context)
    }*/


    /*@Singleton
    @Provides
    fun provideAdmin(
        retrofitBuilder02: RetrofitBuilder02,
        tokenPreferences: TokenManager
    ) : AdminApi {
        return retrofitBuilder02.createServiceWithAuth(AdminApi::class.java, tokenPreferences)
    }*/
}