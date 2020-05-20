package com.example.chuckfacts.repository.remote

import com.example.chuckfacts.util.ChuckFactResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FactsApiInterface {

    /**
     * Requesting facts using Retrofit 2
     * using @GET to talk to the REST API
     */

    @GET("/jokes/random")
    fun fetchRandomFacts(): Call<ChuckFactResponse>

    @GET("/jokes/categories")
    fun fetchAllCategories(): Call<List<String>>

    @GET("/jokes/random")
    fun fetchRandomFromCategory(@Query("category") category: String): Call<ChuckFactResponse>
}