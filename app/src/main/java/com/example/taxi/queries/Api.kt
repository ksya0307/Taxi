package com.example.taxi.queries

import com.example.taxi.Requests.LoginRequest
import com.example.taxi.Requests.ProfileRequest
import com.example.taxi.Requests.SignupRequest
import com.example.taxi.Responses.CarsResponse
import com.example.taxi.Responses.LoginResponse
import com.example.taxi.Responses.ProfileResponse
import com.example.taxi.Responses.SignupResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {

  @POST("login")
  fun login(@Body request:LoginRequest): Call<LoginResponse>

  @POST("signup")
  fun signup(@Body request:SignupRequest):Call<SignupResponse>

  @POST("profile")
  fun profile(@Body request: ProfileRequest):Call<ProfileResponse>

  @GET("cars")
  fun cars(): Call<List<CarsResponse>>
}