package com.example.chuckfacts.util

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "facts_table")
data class ChuckFactResponse(
    val categories: List<String>,

    val created_at: String,

    val icon_url: String,

    @PrimaryKey
    val id: String,

    val updated_at: String,

    val url: String,

    val value: String
)



