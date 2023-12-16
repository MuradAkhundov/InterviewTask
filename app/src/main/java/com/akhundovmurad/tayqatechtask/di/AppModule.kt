package com.akhundovmurad.tayqatechtask.di

import android.content.Context
import com.akhundovmurad.tayqatechtask.data.local.dao.LocalCityDao
import com.akhundovmurad.tayqatechtask.data.local.dao.LocalCountryDao
import com.akhundovmurad.tayqatechtask.data.local.dao.LocalPersonDao
import com.akhundovmurad.tayqatechtask.data.local.entity.MyObjectBox
import com.akhundovmurad.tayqatechtask.data.network.ApiService
import com.akhundovmurad.tayqatechtask.data.network.retroift.ApiUtils
import com.akhundovmurad.tayqatechtask.data.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.objectbox.Box
import io.objectbox.BoxStore
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    @Provides
    @Singleton
    fun provideBoxStore(@ApplicationContext context: Context): BoxStore {
        return MyObjectBox.builder()
            .androidContext(context.applicationContext)
            .build()
    }

    @Provides
    @Singleton
    fun provideMainRepository(
        apiService: ApiService,
        countryDao: LocalCountryDao,
        cityDao: LocalCityDao,
        personDao: LocalPersonDao,
        boxStore: BoxStore
    ): MainRepository {
        return MainRepository(apiService,countryDao,cityDao,personDao,boxStore)
    }

    @Provides
    @Singleton
    fun provideCountryDao(boxStore: BoxStore): LocalCountryDao {
        return LocalCountryDao(boxStore)
    }

    @Provides
    @Singleton
    fun provideCityDao(boxStore: BoxStore): LocalCityDao {
        return LocalCityDao(boxStore)
    }

    @Provides
    @Singleton
    fun providePersonDao(boxStore: BoxStore): LocalPersonDao {
        return LocalPersonDao(boxStore)
    }

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return ApiUtils.getApiService()
    }
}