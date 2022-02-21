package com.sugarspoon.data.di

import com.sugarspoon.data.repositories.EventRepository
import com.sugarspoon.data.repositories.EventRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton

@InstallIn(ViewModelComponent::class)
@Module
interface RepositoryModule {

    @Binds
    abstract fun bindsRepository(repository: EventRepositoryImpl): EventRepository

}