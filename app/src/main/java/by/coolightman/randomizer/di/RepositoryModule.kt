package by.coolightman.randomizer.di

import by.coolightman.randomizer.data.repository.PreferencesRepositoryImpl
import by.coolightman.randomizer.domain.repository.PreferencesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun providePreferencesRepository(impl: PreferencesRepositoryImpl): PreferencesRepository
}