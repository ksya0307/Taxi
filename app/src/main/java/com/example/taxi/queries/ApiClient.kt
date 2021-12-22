package com.example.taxi.queries

import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class ApiClient {
    private lateinit var apiService: Api

    fun getApiService():Api{

        val baseUrl = "http://cars.areas.su/"

        val mosh = Moshi.Builder()
           .build()

        if(!::apiService.isInitialized){
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(MoshiConverterFactory.create(mosh))
                .build()

            apiService = retrofit.create(Api::class.java)
        }

        return apiService
    }
}