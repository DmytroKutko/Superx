package com.superx.heroes.di

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.room.Room
import com.facebook.CallbackManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.superx.heroes.BuildConfig
import com.superx.heroes.api.HeroesApi
import com.superx.heroes.database.HeroDao
import com.superx.heroes.database.LocalDatabase
import com.superx.heroes.database.SuperXPrefs
import com.superx.heroes.database.SuperXPrefsImpl
import com.superx.heroes.database.model.Hero
import com.superx.heroes.feature.auth.domain.use_case.AuthUseCases
import com.superx.heroes.feature.auth.domain.use_case.FacebookSignInUseCase
import com.superx.heroes.feature.auth.domain.use_case.GoogleSignInUseCase
import com.superx.heroes.feature.auth.domain.use_case.SignOutUseCase
import com.superx.heroes.feature.heroes.data.HeroRepositoryImpl
import com.superx.heroes.feature.heroes.data.RemoteHeroRepositoryImpl
import com.superx.heroes.feature.heroes.domain.HeroRepository
import com.superx.heroes.feature.heroes.domain.RemoteHeroRepository
import com.superx.heroes.feature.heroes.domain.use_case.GetAllHeroes
import com.superx.heroes.feature.heroes.domain.use_case.GetHeroById
import com.superx.heroes.feature.heroes.domain.use_case.InsertHeroes
import com.superx.heroes.feature.heroes.domain.use_case.UpdateHero
import com.superx.heroes.feature.heroes.domain.use_case.UseCases
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
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun providesSuperXPrefs(preferences: SharedPreferences): SuperXPrefs =
        SuperXPrefsImpl(preferences)

    @Provides
    @Singleton
    fun provideCallbackManager(): CallbackManager = CallbackManager.Factory.create()

    @Provides
    @Singleton
    fun provideFirebaseAnalytics() = Firebase.analytics

    @Provides
    @Singleton
    fun provideAuth() = Firebase.auth

    @Provides
    @Singleton
    fun provideGoogleSignInClient(@ApplicationContext context: Context): GoogleSignInClient =
        GoogleSignIn.getClient(
            context,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(BuildConfig.WEB_CLIENT_ID)
                .requestEmail()
                .build()
        )

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
    fun provideRemoteHeroRepository(heroesApi: HeroesApi): RemoteHeroRepository =
        RemoteHeroRepositoryImpl(heroesApi)

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
    fun provideGoogleAuthUseCases(auth: FirebaseAuth, googleSignInClient: GoogleSignInClient, prefs: SuperXPrefs) =
        AuthUseCases(
            googleSignIn = GoogleSignInUseCase(
                auth = auth,
                googleSignInClient = googleSignInClient
            ),
            signOut = SignOutUseCase(
                googleSignInClient = googleSignInClient,
                auth = auth,
                prefs = prefs
            ),
            facebookSignIn = FacebookSignInUseCase(
                prefs = prefs
            )
        )


    @Provides
    @Singleton
    fun provideUpdateListSharedFlow(): MutableSharedFlow<Hero> = MutableSharedFlow()

    @Provides
    @Singleton
    fun provideOnActivityResultFlow(): MutableSharedFlow<Pair<Int, Intent?>> = MutableSharedFlow()

}