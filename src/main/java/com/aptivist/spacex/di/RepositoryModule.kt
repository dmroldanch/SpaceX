package com.aptivist.spacex.di

import com.aptivist.spacex.data.api.spacex.repository.LaunchesFetchingImpl
import com.aptivist.spacex.domain.repository.RepositoryLauches
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindRepositoryLaunches(implementation: LaunchesFetchingImpl) : RepositoryLauches
}