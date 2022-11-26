package com.elmohandes.task2grand.mvvm

import android.app.Application
import com.elmohandes.task2grand.database.ArticleDatabase
import com.elmohandes.task2grand.model.DataX

class ArticleRepo(application: Application) {

    private var database: ArticleDatabase

    init {
        database = ArticleDatabase(application)
    }

    suspend fun insertArticle(dataX: DataX) = database.dao().insertArticles(dataX)

    fun getAllArticles() = database.dao().getAllArticles()

    suspend fun deleteArticle(dataX: DataX) = database.dao().deleteArticle(dataX)

}