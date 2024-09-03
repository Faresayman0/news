package com.example.whatnew

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsCallable {
    @GET("/v2/everything")
    fun getNews(@Query("q") query: String, @Query("apiKey") apiKey: String): Call<News>

    @GET("/v2/top-headlines")
    fun getNewsByCountry(@Query("country") country: String, @Query("apiKey") apiKey: String): Call<News>
}
