package com.service.mvisample.view.viewstate

import com.service.mvisample.model.User


sealed class LandingScreenState {
    object Idle : LandingScreenState()
    object Loading : LandingScreenState()
    data class Users(val user: List<User>) : LandingScreenState()
    data class Error(val error: String?) : LandingScreenState()

}