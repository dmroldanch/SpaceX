package com.aptivist.spacex.di

import android.content.Context
import com.aptivist.spacex.BuildConfig
import com.aptivist.spacex.domain.IImageLoader
import com.aptivist.spacex.domain.framework.GlideImageLoader
import com.aptivist.spacex.domain.framework.PicassoImageLoader
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.squareup.picasso.Picasso
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

object AppModule {


    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .writeTimeout(10L, TimeUnit.SECONDS)
        .readTimeout(10L, TimeUnit.SECONDS)
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                if (BuildConfig.DEBUG) {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            }
        )
        .build()


    @Singleton
    @Provides
    fun provideretrofit(okHttpClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    
    @Singleton
    @Provides
    fun providePicasso() : Picasso = Picasso.get()

    // 😎👈🏼 By Chuy, Diego, Irving and Dan (👍 ͡❛ ͜ʖ ͡❛)👍
    @Singleton
    @Provides
    fun provideGlide(@ApplicationContext context : Context) : RequestManager {
        return Glide.with(context)
    }
    
    @Singleton
    @Provides
    fun provideImageLoader(picasso: Picasso) : IImageLoader = PicassoImageLoader(picasso)

    /*@Singleton
    @Provides
    fun provideImageLoader(requestManager: RequestManager) : IImageLoader = GlideImageLoader(requestManager)*/
}