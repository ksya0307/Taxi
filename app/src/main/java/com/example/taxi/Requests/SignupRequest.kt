package com.example.taxi.Requests

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SignupRequest(
    @Json(name = "username")
    val username:String,
    @Json(name = "email")
    val email: String,
    @Json(name = "password")
    val password:String
)
