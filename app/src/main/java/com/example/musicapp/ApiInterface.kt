package com.example.musicapp

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiInterface {
    @Headers(
        "X-RapidAPI-Key: 1c4463ea4emsh91e508fa6448c37p1bfeb1jsn65982bb76962",
        "X-RapidAPI-Host: deezerdevs-deezer.p.rapidapi.com"
    )
    @GET("search")
    suspend fun getData(@Query("q") query: String): Response<MyData>
}