package com.service.mvisample.model.repository.api

import android.util.Log
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.service.mvisample.commonutils.extensions.convertIntoModels
import com.service.mvisample.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.await
import javax.inject.Inject

class MVIRepositoryImpl @Inject constructor(
    private val retrofit: Retrofit
) : MVIRepository {
    override suspend fun getUsers(): Flow<List<User>> =
        flow {
            try {
                val response = retrofit.create(NetworkApiCallInterface::class.java).makeHttpGetRequest().await()
                val userList = response.toString().convertIntoModels(object : TypeToken<List<User>>() {})
                if(userList?.isNotEmpty() == true){
                    emit(userList)
                }
            } catch (e: Exception) {
                Log.e("getMediaCategory", "${e.message}")
                emit(emptyList())
            }
        }


}
