package com.example.chuckfacts.repository.local

import androidx.room.*
import com.example.chuckfacts.util.ChuckFactResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface FactDao {

    /** Using Coroutines to get data from DataBase
     */

    @Query("SELECT * FROM  facts_table")
    fun fetchAllFacts(): Flow<List<ChuckFactResponse>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFactToDb(fact: ChuckFactResponse)

    @Query("DELETE FROM facts_table WHERE id = :factId")
    fun deleteFactFromDb(factId: String)
}