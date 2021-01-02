package com.example.chuckfacts.viewmodel

import android.app.Application
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.chuckfacts.repository.FactsApiService
import com.example.chuckfacts.repository.FactsRepo
import com.example.chuckfacts.util.ChuckFactResponse
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

// TODO: Add handling onFailure and when when we get null objects, inform user, also check if device is online wifi+cellular
// Can create error chuck obj, and pass it through live data ?

class FactsViewModel(application: Application) : AndroidViewModel(application) {
    private val factsRepo: FactsRepo = FactsRepo(FactsApiService.factsApi, application)

    private val fact: MutableLiveData<ChuckFactResponse> = MutableLiveData<ChuckFactResponse>()
    private val categories: MutableLiveData<List<String>> = MutableLiveData<List<String>>()
    private val savedFacts: MutableLiveData<List<ChuckFactResponse>> = MutableLiveData<List<ChuckFactResponse>>()

    init {

    }

    private fun handleNewResponse(chuckFact: ChuckFactResponse){
        Timber.i("ID: ${chuckFact.id}")
        Timber.i("Fact: ${chuckFact.value}")
        fact.value = chuckFact
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

                categories.value = categoriesList
            }
        })
    }

    fun getAllSavedFacts(){
        viewModelScope.launch {
            factsRepo.getAllSavedFacts()?.collect {
                savedFacts.postValue(it)
            }
        }
    }

    fun deleteFact(fact: ChuckFactResponse){
        viewModelScope.launch {
            factsRepo.deleteFactFromDb(fact.id)
        }
    }

    fun saveFact(fact: ChuckFactResponse){
        viewModelScope.launch {
            factsRepo.saveFactToDb(fact)
        }
    }


    fun getFactsLiveData(): LiveData<ChuckFactResponse> = fact
    fun getAllCategoriesLiveData(): LiveData<List<String>> = categories
    fun getAllSavedFactsLiveData(): LiveData<List<ChuckFactResponse>> = savedFacts
}
