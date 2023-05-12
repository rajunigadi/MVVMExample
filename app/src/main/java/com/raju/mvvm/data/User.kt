package com.raju.mvvm.data

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("login") var name: String? = null,
    @SerializedName("avatar_url") var avatarUrl: String? = null
)