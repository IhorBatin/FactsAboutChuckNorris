package com.example.chuckfacts.repository.local

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class Converters {

    // Creates Moshi adapter for List<String>
    private val stringListAdapter: JsonAdapter<List<String>> by lazy {
        Types.newParameterizedType(List::class.java, String::class.java)
            .let { Moshi.Builder().build().adapter<List<String>>(it) }
    }

    @TypeConverter
    fun fromStringList(strings: List<String>): String = stringListAdapter.toJson(strings)

    @TypeConverter
    fun fromJsonString(json: String): List<String> = stringListAdapter.fromJson(json) ?: listOf()
}