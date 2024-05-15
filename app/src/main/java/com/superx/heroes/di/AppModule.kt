package com.superx.heroes.di

import android.content.Context
import androidx.room.Room
import com.superx.heroes.api.HeroesApi
import com.superx.heroes.database.HeroDao
import com.superx.heroes.database.LocalDatabase
import com.superx.heroes.database.model.Hero
import com.superx.heroes.feature.heroes.data.HeroRepositoryImpl
import com.superx.heroes.feature.heroes.data.RemoteHeroRepositoryImpl
import com.superx.heroes.feature.heroes.domain.HeroRepository
import com.superx.heroes.feature.heroes.domain.RemoteHeroRepository
import com.superx.heroes.feature.heroes.domain.use_case.GetAllHeroes
import com.superx.heroes.feature.heroes.domain.use_case.GetHeroById
import com.superx.heroes.feature.heroes.domain.use_case.InsertHeroes
import com.superx.heroes.feature.heroes.domain.use_case.UpdateHero
import com.superx.heroes.feature.heroes.domain.use_case.UseCases
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.superx.heroes.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.MutableSharedFlow
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            LocalDatabase::class.java,
            "local_database"
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideHeroDao(database: LocalDatabase) = database.heroDao()

    @Provides
    @Singleton
    fun provideHeroRepository(heroDao: HeroDao): HeroRepository = HeroRepositoryImpl(dao = heroDao)

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    @Provides
    @Singleton
    fun provideHeroesApi(retrofit: Retrofit): HeroesApi = retrofit.create(HeroesApi::class.java)

    @Provides
    @Singleton
    fun provideRemoteHeroRepository(heroesApi: HeroesApi): RemoteHeroRepository = RemoteHeroRepositoryImpl(heroesApi)

    @Provides
    @Singleton
    fun provideUseCases(repository: HeroRepository, remoteRepository: RemoteHeroRepository) =
        UseCases(
            insertHeroes = InsertHeroes(repository = repository),
            updateHero = UpdateHero(repository = repository),
            getHeroById = GetHeroById(repository = repository),
            getAllHeroes = GetAllHeroes(
                repository = repository,
                remoteHeroRepository = remoteRepository
            )
        )

    @Provides
    @Singleton
    fun provideUpdateListSharedFlow(): MutableSharedFlow<Hero> = MutableSharedFlow()
}