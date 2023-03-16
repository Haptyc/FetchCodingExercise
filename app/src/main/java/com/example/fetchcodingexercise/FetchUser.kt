package com.example.fetchcodingexercise

import com.google.gson.annotations.SerializedName

data class FetchUser(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("listId")
    var listId: Int = 0,
    @SerializedName("name")
    var name: String?
)