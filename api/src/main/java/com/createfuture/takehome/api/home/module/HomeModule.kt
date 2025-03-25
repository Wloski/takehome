package com.createfuture.takehome.api.home.module

import com.createfuture.takehome.api.home.repository.CharacterRepository
import com.createfuture.takehome.api.home.repository.CharacterRepositoryImpl
import com.createfuture.takehome.api.home.service.CharacterApi
import com.createfuture.takehome.api.home.service.CharacterService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HomeModule {

    @Singleton
    @Provides
    fun provideCharacterRepository(
        characterService: CharacterService,
    ): CharacterRepository {
        return CharacterRepositoryImpl(characterService)
    }

    @Singleton
    @Provides
    fun provideCharacterService(
        retrofit: Retrofit,
    ): CharacterService {
        return CharacterService(retrofit)
    }

    @Provides
    fun provideRetrofit(): Retrofit {
        // TODO Add okhttp interceptor for token
        return Retrofit.Builder()
            .baseUrl("https://yj8ke8qonl.execute-api.eu-west-1.amazonaws.com")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(OkHttpClient.Builder().build()).build()
    }
}