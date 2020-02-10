package com.example.mytweets.Network

import com.google.gson.annotations.SerializedName

class CustomObject(
    val customname: String,
    val customtext: String,
    val custonurl:String,
    val customscreenname:String) {
}
class TwitterUser {

    @SerializedName("access_token")
    var access_token: String? = null
}
class UserDetails {
    @SerializedName("statuses")
    var statuses: Array<Users>? = null
}
class Users{
    @SerializedName("user")
    var user: User?=null
    @SerializedName("text")
    var text:String= ""
}
class User{
    @SerializedName("name")
    var name: String? = null
    @SerializedName("screen_name")
    var screen_name: String? = null
    @SerializedName("profile_image_url")
    var profile_image_url: String? = null
}
