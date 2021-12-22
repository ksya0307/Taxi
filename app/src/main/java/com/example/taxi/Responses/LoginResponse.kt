package com.example.taxi.Responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginResponse(
    @Json(name = "notice") val notice: Map<String, Any>
){
    @JsonClass(generateAdapter = true)
    data class Notice(
        @Json(name = "token") val token:Int,
        @Json(name = "answer") val answer:String
    )
}
