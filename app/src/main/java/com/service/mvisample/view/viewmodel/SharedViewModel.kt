package com.service.mvisample.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.service.mvisample.intent.LandingScreenIntent
import com.service.mvisample.model.repository.api.MVIRepositoryImpl
import com.service.mvisample.view.viewstate.LandingScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val mviRepositoryImpl: MVIRepositoryImpl
) : ViewModel() {
    val userIntent = Channel<LandingScreenIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<LandingScreenState>(LandingScreenState.Idle)
    val state: StateFlow<LandingScreenState>
        get() = _state

    init {
        handleIntent()
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
            mviRepositoryImpl.getUsers().catch {
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
}