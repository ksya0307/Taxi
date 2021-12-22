package com.example.taxi.Responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CarsResponse(
    @Json(name = "id") val id:String,
    @Json(name = "model") val model:String,
    @Json(name = "lon") val lon:String,
    @Json(name = "lat") val lat:String
)
