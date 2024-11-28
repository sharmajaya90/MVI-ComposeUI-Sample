package com.service.mvisample.model.repository.api

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.service.mvisample.model.User
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url


interface NetworkApiCallInterface {
    @GET("users")
    fun makeHttpGetRequest(): Call<JsonArray>
}
