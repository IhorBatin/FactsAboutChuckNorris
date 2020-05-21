package com.example.chuckfacts.repository.remote

import com.example.chuckfacts.util.ChuckFactResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

private const val URL_RANDOM =  "/jokes/random"
private const val URL_CATEGORIES =  "/jokes/categories"

interface FactsApiInterface {
    /**
     * Requesting facts using Retrofit 2
     * using @GET to talk to the REST API
     */

    @GET(URL_RANDOM)
    fun fetchRandomFacts(): Call<ChuckFactResponse>

    @GET(URL_RANDOM)
    fun fetchRandomFacts(@Query("category") category: String): Call<ChuckFactResponse>

    @GET(URL_CATEGORIES)
    fun fetchAllCategories(): Call<List<String>>
}