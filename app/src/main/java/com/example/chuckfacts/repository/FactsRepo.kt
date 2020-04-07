package com.example.chuckfacts.repository

import com.example.chuckfacts.repository.remote.FactsApiInterface

class FactsRepo(private val factsApiInterface: FactsApiInterface) {
    fun getRandomFacts() = factsApiInterface.fetchRandomFacts()
}