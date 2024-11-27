package com.service.mvisample.view.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.reflect.TypeToken
import com.service.mvisample.commonutils.extensions.convertIntoModels
import com.service.mvisample.intent.LandingScreenIntent
import com.service.mvisample.model.User
import com.service.mvisample.model.repository.api.MVIRepositoryImpl
import com.service.mvisample.model.repository.api.NetworkApiCallInterface
import com.service.mvisample.view.viewstate.LandingScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.await
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val retrofit: Retrofit
) : ViewModel() {
    val userIntent = Channel<LandingScreenIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<LandingScreenState>(LandingScreenState.Idle)
    val state: StateFlow<LandingScreenState>
        get() = _state

    init {
        handleIntent()
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


    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when (it) {
                    is LandingScreenIntent.LandingScreen -> fetchUser()
                }
            }
        }
    }


    private fun fetchUser() {
        viewModelScope.launch {
            _state.value = LandingScreenState.Loading
            getUsers().catch {
                _state.value = LandingScreenState.Error("No Users Found!!!")
            }.collect {
                _state.value =  LandingScreenState.Users(it)
            }
            /*_state.value = try {
                LandingScreenState.Users(mviRepositoryImpl.getUsers())
            } catch (e: Exception) {
                LandingScreenState.Error(e.localizedMessage)
            }*/
        }
    }

    fun userIntent(){
        viewModelScope.launch {
            userIntent.send(LandingScreenIntent.LandingScreen)
        }
    }
}