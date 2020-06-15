package com.example.chuckfacts.repository

import android.app.Application
import com.example.chuckfacts.repository.local.FactDao
import com.example.chuckfacts.repository.local.FactsDb
import com.example.chuckfacts.repository.remote.FactsApiInterface
import com.example.chuckfacts.util.ChuckFactResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

class FactsRepo(
    private val factsApiInterface: FactsApiInterface,
    application: Application
) : CoroutineScope{

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var factDao: FactDao?

    init{
        val db: FactsDb? = FactsDb.getDataBase(application)
        factDao = db?.factsDao()
    }

    // Remote calls
    fun getRandomFacts() = factsApiInterface.fetchRandomFacts()
    fun getRandomFacts(category: String) = factsApiInterface.fetchRandomFacts(category)
    fun getAllCategories() = factsApiInterface.fetchAllCategories()

    // Local calls
    fun getAllSavedFacts() = factDao?.fetchAllFacts()

    // Launching Coroutine to save fact in DB
    fun saveFactToDb(fact: ChuckFactResponse){
        launch {
            withContext(Dispatchers.IO){
                factDao?.insertFactToDb(fact)
            }
        }
    }

}