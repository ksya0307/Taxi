package com.example.taxi.Responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProfileResponse(
    @Json(name = "username") val username:String,
    @Json(name = "firstname") val firstname:String,
    @Json(name = "secondname") val secondname:String,
    @Json(name = "email") val email:String,
    @Json(name = "timeDrive") val timeDrive:String,
    @Json(name = "cash") val cash:String,
)
