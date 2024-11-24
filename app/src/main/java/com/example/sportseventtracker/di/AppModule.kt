package com.example.sportseventtracker.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.sportseventtracker.data.SportsApi
import com.example.sportseventtracker.data.SportsRepositoryImpl
import com.example.sportseventtracker.data.local.FavoriteDao
import com.example.sportseventtracker.data.local.FavoriteDatabase
import com.example.sportseventtracker.domain.SportsRepository
import com.example.sportseventtracker.utils.StringProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://618d3aa7fe09aa001744060a.mockapi.io/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideSportsApi(retrofit: Retrofit): SportsApi {
        return retrofit.create(SportsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSportsRepository(api: SportsApi, dao: FavoriteDao): SportsRepository {
        return SportsRepositoryImpl(api, dao)
    }

    @Provides
    @Singleton
    fun provideStringProvider(@ApplicationContext context: Context?): StringProvider {
        return StringProvider(context!!)
    }

    @Provides
    @Singleton
    fun provideFavoriteDatabase(application: Application): FavoriteDatabase {
        return Room.databaseBuilder(
            application.applicationContext,
            FavoriteDatabase::class.java,
            "favorite-database"
        ).build()
    }

    @Provides
    fun provideFavoriteDao(appDatabase: FavoriteDatabase): FavoriteDao {
        return appDatabase.favoriteDao()
    }
}
