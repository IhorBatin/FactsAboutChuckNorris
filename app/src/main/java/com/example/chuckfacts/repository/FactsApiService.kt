package com.example.chuckfacts.repository

import com.example.chuckfacts.repository.remote.FactsApiInterface
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object FactsApiService {
    private val BASE_URL = "https://api.chucknorris.io/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val factsApi: FactsApiInterface = retrofit.create(FactsApiInterface::class.java)
}