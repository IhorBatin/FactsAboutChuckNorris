package com.example.chuckfacts.repository

import com.example.chuckfacts.repository.remote.FactsApiInterface
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object FactsApiService {
    private const val BASE_URL: String = "https://api.chucknorris.io"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val factsApi: FactsApiInterface = retrofit.create(FactsApiInterface::class.java)
}