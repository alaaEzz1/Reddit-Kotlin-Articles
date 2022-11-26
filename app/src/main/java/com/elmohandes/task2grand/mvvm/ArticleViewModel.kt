package com.elmohandes.task2grand.mvvm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.elmohandes.task2grand.model.DataX
import kotlinx.coroutines.launch

class ArticleViewModel(application: Application) : AndroidViewModel(application) {


    private var repo: ArticleRepo?
    var getArticles : LiveData<List<DataX>>
    init {
        repo = ArticleRepo(application)
        getArticles = repo!!.getAllArticles()
    }

    fun insertArticle(dataX: DataX) = viewModelScope.launch {
        repo!!.insertArticle(dataX)
    }

    fun deleteArticle(dataX: DataX) = viewModelScope.launch {
        repo!!.deleteArticle(dataX)
    }

}