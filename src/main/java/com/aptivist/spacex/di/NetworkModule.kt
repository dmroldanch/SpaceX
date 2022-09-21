package com.aptivist.spacex.di

import com.aptivist.spacex.data.api.spacex.implementations.SpaceXDataSource
import com.aptivist.spacex.data.api.spacex.retrofit.ISpaceXAPI
import com.aptivist.spacex.domain.ISpaceXDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun providePokemonAPI(retrofit: Retrofit) : ISpaceXAPI {
        return retrofit.create(ISpaceXAPI::class.java)
    }

    @Singleton
    @Provides
    fun providePokemonDataSource(pokeAPI: ISpaceXAPI) : ISpaceXDataSource = SpaceXDataSource(pokeAPI)

}