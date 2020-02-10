package com.example.mytweets.TweeterOverview

import android.util.Base64
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mytweets.Network.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.net.URLEncoder

class TweetViewModel :ViewModel(){

   val _resMutable = MutableLiveData<ArrayList<CustomObject>>()
    val resLive: LiveData<ArrayList<CustomObject>>
        get() = _resMutable
    var Consumer_Key:String="P1CCkrYfBIx6HeN1nhRb3g"
    var Secret_Key :String="nBzpTDSMo20SoUOhE1pm1ngqEfCAIdSXI0x0VOgYk"
    val resArrayList: ArrayList<CustomObject> = ArrayList()
    fun OnClick(Search_text:String) {

        val urlApiKey: String = URLEncoder.encode(Consumer_Key, "UTF-8")
        val urlApiSecret: String = URLEncoder.encode(Secret_Key, "UTF-8")
        val combined = "$urlApiKey:$urlApiSecret"
        val base64Encoded = Base64.encodeToString(combined.toByteArray(), Base64.NO_WRAP)
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        Retrofit.Builder().client(httpClient.build())
        TweetApi.retrofitService.createUser(auth = "Basic "+base64Encoded)?.enqueue(object : Callback<TwitterUser?> {
            override fun onFailure(call: Call<TwitterUser?>, t: Throwable) {
                Log.i("main","$t Cant connect")
            }
            override fun onResponse(call: Call<TwitterUser?>, response: Response<TwitterUser?>) {
                var usres = response.body()
                var user = usres?.access_token
                Log.i("main","$user")

                TweetApi.retrofitService.getUser(auth = "Bearer "+user.toString(),q = Search_text)?.enqueue(object : Callback<UserDetails> {
                    override fun onFailure(call: Call<UserDetails>, t: Throwable) {
                        Log.i("main","Cant Get Data")
                    }
                    override fun onResponse(call: Call<UserDetails>, response: Response<UserDetails>) {
                        resArrayList.clear()
                        var rbody=response.body()
                        var formainbody=rbody?.statuses
                        for(i in 0 until formainbody!!.size){
                            var fortext=formainbody?.get(i)?.text
                            var forname=formainbody?.get(i)?.user?.name
                            var forimg=formainbody?.get(i)?.user?.profile_image_url
                            var forscreenname="@"+formainbody?.get(i)?.user?.screen_name
                            resArrayList.add(CustomObject(forname.toString(), fortext, forimg.toString(), forscreenname))
                        }
                        _resMutable.value=resArrayList
                    }
                })
            }
        })
    }
}
