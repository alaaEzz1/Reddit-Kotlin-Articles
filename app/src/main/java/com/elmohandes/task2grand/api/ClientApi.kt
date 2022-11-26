package com.elmohandes.task2grand.api

import com.elmohandes.task2grand.model.Children
import com.elmohandes.task2grand.model.RedditModel
import retrofit2.Call
import retrofit2.http.GET

interface ClientApi {
    @GET(".json")
    fun getAllArticles() : Call<RedditModel?>?
}