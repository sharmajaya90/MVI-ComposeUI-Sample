package com.service.mvisample.view.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.reflect.TypeToken
import com.service.mvisample.commonutils.extensions.convertIntoModels
import com.service.mvisample.intent.HomeIntent
import com.service.mvisample.model.User
import com.service.mvisample.model.repository.api.NetworkApiCallInterface
import com.service.mvisample.view.viewstate.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.await
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val retrofit: Retrofit
) : ViewModel() {
    val homeIntent = Channel<HomeIntent>(Channel.UNLIMITED)
    var _state by mutableStateOf(HomeUiState())


    fun onEvent(event: HomeIntent) {
        when (event) {
            HomeIntent.DefaultScreen -> _state =
                _state.copy( defaultScreen = "Please Sync User Listing...")
            HomeIntent.FetchUsers -> fetchUser()
            is HomeIntent.OnUserSelected -> _state =
                _state.copy( selectedUser = event.selectedUser)
        }
    }


    suspend fun getUsers(): Flow<List<User>> {
        return flow {
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

    private fun fetchUser() {
        viewModelScope.launch {
            _state = _state.copy(loading = true)
            getUsers().catch {
                _state = _state.copy(
                    loading = false,
                    errorMessage = it.message
                )
            }.collect {
                _state = _state.copy(
                    loading = false,
                    users = it
                )
            }
        }
    }

    fun userIntent(){
        viewModelScope.launch {
            homeIntent.send(HomeIntent.FetchUsers)
        }
    }
}