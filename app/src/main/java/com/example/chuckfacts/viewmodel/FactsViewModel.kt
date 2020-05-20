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

class FactsViewModel : ViewModel() {
    private val factsRepo: FactsRepo = FactsRepo(FactsApiService.factsApi)

    private val fact: MutableLiveData<ChuckFactResponse> = MutableLiveData<ChuckFactResponse>()
    private val categories: MutableLiveData<List<String>> = MutableLiveData<List<String>>()
    private val factFromCategory: MutableLiveData<ChuckFactResponse> = MutableLiveData<ChuckFactResponse>()

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

                val chuckFact: ChuckFactResponse = response.body()!!
                Timber.i("ID: ${chuckFact.id}")
                Timber.i("Fact: ${chuckFact.value}")
                fact.postValue(chuckFact)
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

    fun getRandomByCategory(specificCategory: String){
        factsRepo.getRandomFromCategory(specificCategory)
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

                    val chuckFact: ChuckFactResponse = response.body()!!
                    Timber.i("ID: ${chuckFact.id}")
                    Timber.i("Fact from Category: ${chuckFact.value}")
                    factFromCategory.postValue(chuckFact)
                }
            })
    }

    fun getFactsLiveData(): LiveData<ChuckFactResponse> = fact
    fun getAllCategoriesLiveData(): LiveData<List<String>> = categories
    fun getFromCategoryLiveData(): LiveData<ChuckFactResponse> = factFromCategory

}
