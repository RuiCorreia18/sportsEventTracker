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
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    @Singleton
    fun provideUnsafeOkHttpClient(): OkHttpClient {
        // Unsafe OkHttpClient to bypass SSL issues on older devices.
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
        })

        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())

        val sslSocketFactory = sslContext.socketFactory

        return OkHttpClient.Builder()
            .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            .hostnameVerifier { _, _ -> true }
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://618d3aa7fe09aa001744060a.mockapi.io/api/")
            .client(okHttpClient) // Use the custom OkHttpClient
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
