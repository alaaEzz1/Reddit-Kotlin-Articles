package com.elmohandes.task2grand.api

import com.elmohandes.task2grand.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object{
        private val retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(logging).build()
            Retrofit.Builder().baseUrl(Constants.REDDIT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client).build()

        }

        val api: ClientApi by lazy {
            retrofit.create(ClientApi::class.java)
        }
    }
}