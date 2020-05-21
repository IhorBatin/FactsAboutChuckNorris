package com.example.chuckfacts.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chuckfacts.repository.FactsApiService
import com.example.chuckfacts.repository.FactsRepo
import com.example.chuckfacts.util.ChuckFactResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

// TODO: Add handling onFailure and when when we get null objects, inform user

class FactsViewModel : ViewModel() {
    private val factsRepo: FactsRepo = FactsRepo(FactsApiService.factsApi)
    private val factsList: MutableList<ChuckFactResponse> = mutableListOf<ChuckFactResponse>()
    private val fact: MutableLiveData<ChuckFactResponse> = MutableLiveData<ChuckFactResponse>()
    private val categories: MutableLiveData<List<String>> = MutableLiveData<List<String>>()

    private fun handleNewResponse(chuckFact: ChuckFactResponse){
        Timber.i("ID: ${chuckFact.id}")
        Timber.i("Fact: ${chuckFact.value}")
        factsList.add(chuckFact)
        fact.postValue(chuckFact)
    }

    fun getPreviousFact(index: Int){
        fact.postValue(factsList[index])
    }

    fun getRandomFact(){
        factsRepo.getRandomFacts().enqueue(object : Callback<ChuckFactResponse>{
            override fun onFailure(call: Call<ChuckFactResponse>, t: Throwable) {
                Timber.e("Random Facts onFailure: ${t.message}")
            }

            override fun onResponse(
                call: Call<ChuckFactResponse>,
                response: Response<ChuckFactResponse>) {
                Timber.i("Random Facts onResponse... \n")

                if(response.body() == null){
                    Timber.i("Response is null")
                    return
                }

                handleNewResponse(response.body()!!)
            }
        })
    }

    fun getRandomFact(specificCategory: String){
        factsRepo.getRandomFacts(specificCategory)
            .enqueue(object : Callback<ChuckFactResponse>{
                override fun onFailure(call: Call<ChuckFactResponse>, t: Throwable) {
                    Timber.i("Fact fom Category onFailure: ${t.message}")
                }

                override fun onResponse(
                    call: Call<ChuckFactResponse>,
                    response: Response<ChuckFactResponse>
                ) {
                    Timber.i("Fact fom Category onResponse...")

                    if(response.body() == null){
                        Timber.i("Response is null")
                        return
                    }

                    handleNewResponse(response.body()!!)
                }
            })
    }

    fun getAllCategories(){
        factsRepo.getAllCategories().enqueue(object : Callback<List<String>>{
            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                Timber.e("Categories onFailure: ${t.message}")
            }

            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                Timber.i("Categories onResponse...")

                if(response.body() == null){
                    Timber.i("Response is null")
                    return
                }

                val categoriesList: List<String> = response.body()!!
                Timber.i("We got ${categoriesList.size} Chuck categories:")

                // Printing all categories to the console
                var catString: String = "["
                for(category in categoriesList) {
                    catString += "$category, "
                }
                catString += "]"
                Timber.i(catString)

                categories.postValue(categoriesList)
            }
        })
    }

    fun getFactsLiveData(): LiveData<ChuckFactResponse> = fact
    fun getAllCategoriesLiveData(): LiveData<List<String>> = categories

}
