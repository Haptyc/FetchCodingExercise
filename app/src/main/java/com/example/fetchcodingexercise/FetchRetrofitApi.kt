package com.example.fetchcodingexercise

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface FetchRetrofitApi {
    @GET("/hiring.json")
    suspend fun getInfo(): Response<ArrayList<FetchUser>>

    companion object {
        private val FETCH_RETROFIT = Retrofit.Builder()
            .baseUrl("https://fetch-hiring.s3.amazonaws.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val FETCH_API_SERVICE = FETCH_RETROFIT.create(FetchRetrofitApi::class.java)
    }
}