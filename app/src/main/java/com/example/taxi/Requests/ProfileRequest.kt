package com.example.taxi.Requests

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProfileRequest(
    @Json(name = "token") val token:Int
)
