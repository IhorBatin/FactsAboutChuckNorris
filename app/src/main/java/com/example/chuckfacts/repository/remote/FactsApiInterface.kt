package com.example.chuckfacts.repository.remote

import com.example.chuckfacts.util.ChuckFactResponse
import retrofit2.Call
import retrofit2.http.GET

interface FactsApiInterface {

    /**
     * Requesting random fact using Retrofit 2
     *  @GET to talk to the API
     *  Retrofit will implement our function
     *  and return Call<T> ?
     */
    @GET("jokes/random")
    fun fetchRandomFacts(): Call<ChuckFactResponse>
}