package com.task.forecastapp.di

import com.task.forecastapp.data.RetrofitService
import com.task.forecastapp.data.repository.AppRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import domain.repository.AppRepository
import domain.usecase.GetCurrentWeatherUseCase
import domain.usecase.GetSearchResultsUseCase
import domain.usecase.GetWeatherForecastUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppUseCases(repository: AppRepository): GetSearchResultsUseCase {
        return GetSearchResultsUseCase(repository)
    }
    @Provides
    @Singleton
    fun provideWeatherForecastUseCases(repository: AppRepository): GetWeatherForecastUseCase {
        return GetWeatherForecastUseCase(repository)
    }
    @Provides
    @Singleton
    fun provideCurrentWeatherUseCases(repository: AppRepository): GetCurrentWeatherUseCase {
        return GetCurrentWeatherUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideEntityRepository(
        retrofitService: RetrofitService,
    ): AppRepository {
        return AppRepositoryImpl( retrofitService)
    }
}