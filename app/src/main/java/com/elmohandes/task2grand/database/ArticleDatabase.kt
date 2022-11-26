package com.elmohandes.task2grand.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.elmohandes.task2grand.model.DataX

@Database(entities = [DataX::class], version = 1)
@TypeConverters(ArticleConverters::class)
abstract class ArticleDatabase : RoomDatabase() {

    abstract fun dao() :ArticleDao

    companion object{
        @Volatile
        private var instance : ArticleDatabase? = null
        private val lock = Any()

        operator fun invoke(context: Context) = instance?: synchronized(lock){
            //everything is happening in this block of function
            // accessed by other thread at the same time
            instance ?: createDatabase(context).also { instance = it}
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(context,ArticleDatabase::class.java,"ArticleDatabase").build()
    }

}