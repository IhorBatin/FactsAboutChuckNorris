package com.example.chuckfacts.viewmodel

import androidx.lifecycle.ViewModel
import com.example.chuckfacts.repository.FactsApiService
import com.example.chuckfacts.repository.FactsRepo
import com.example.chuckfacts.util.ChuckFactResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class FactsViewModel : ViewModel() {
    private val factsRepo: FactsRepo = FactsRepo(FactsApiService.factsApi)

    fun getRandomFact(){
        factsRepo.getRandomFacts().enqueue(object : Callback<ChuckFactResponse>{
            override fun onFailure(call: Call<ChuckFactResponse>, t: Throwable) {
                Timber.i("onFailure: ${t.message}")
            }

            override fun onResponse(
                call: Call<ChuckFactResponse>,
                response: Response<ChuckFactResponse>) {
                Timber.i("onResponse... \n")

                if(response.body() == null){
                    Timber.i("Response is null")
                    return
                }

                val chuckFacts: ChuckFactResponse = response.body()!!
                Timber.i("Fact: ${chuckFacts.value}")
            }
        })
    }
}