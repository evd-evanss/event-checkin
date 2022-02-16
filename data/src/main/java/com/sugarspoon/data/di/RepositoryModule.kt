package com.sugarspoon.data.di

import com.sugarspoon.data.repositories.EventRepository
import com.sugarspoon.data.repositories.EventRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    abstract fun bindsRepository(repository: EventRepositoryImpl): EventRepository

}