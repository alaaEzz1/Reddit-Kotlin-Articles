package com.elmohandes.task2grand.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elmohandes.task2grand.model.DataX

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(dataX: DataX)

    @Query("SELECT * FROM ArticleData")
    fun getAllArticles() : LiveData<List<DataX>>

    @Delete
    suspend fun deleteArticle(dataX: DataX)

}