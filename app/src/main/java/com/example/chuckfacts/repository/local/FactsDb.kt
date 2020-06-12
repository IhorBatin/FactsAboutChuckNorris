package com.example.chuckfacts.repository.local

import android.content.Context
import androidx.room.*
import com.example.chuckfacts.util.ChuckFactResponse


@Database(entities = [ChuckFactResponse::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class FactsDb : RoomDatabase() {

    abstract fun factsDao(): FactDao

    companion object{

        @Volatile
        private var INSTANCE: FactsDb? = null

        fun getDataBase(context: Context): FactsDb? {
            if(INSTANCE == null){
                synchronized(FactsDb::class.java){
                    if(INSTANCE == null){
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            FactsDb::class.java, "facts_database"
                        ).build()
                    }
                }
            }
            return INSTANCE
        }
    }
}