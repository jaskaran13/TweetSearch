package com.example.mytweets.Network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*



var retrofit: Retrofit = Retrofit.Builder()
    //.addConverterFactory(MoshiConverterFactory.create(moshi))
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl("https://api.twitter.com/")
//    .client(httpClient.build())
    .build()
object TweetApi {

    val retrofitService = retrofit.create(TweetDataSource::class.java)
}

interface TweetDataSource{
    @Headers(
        "Content-Type: application/x-www-form-urlencoded",
        "charset:UTF-8",
        "Accept-Encoding:application/x-www-form-urlencoded",
        "Accept-Encoding:application/json"
    )
    @POST("oauth2/token?grant_type=client_credentials")
    fun createUser(@Header("Authorization")auth:String): Call<TwitterUser?>?

    @GET("1.1/search/tweets.json")
    fun getUser(@Header("Authorization")auth:String,@Query("q")q:String): Call<UserDetails>?
}

