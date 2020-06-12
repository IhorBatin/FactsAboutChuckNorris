package com.example.chuckfacts.repository

import android.app.Application
import com.example.chuckfacts.repository.local.FactDao
import com.example.chuckfacts.repository.local.FactsDb
import com.example.chuckfacts.repository.remote.FactsApiInterface
import timber.log.Timber

class FactsRepo(
    private val factsApiInterface: FactsApiInterface,
    application: Application
){

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

}