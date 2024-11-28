package com.service.mvisample.intent

import com.service.mvisample.model.User

sealed class HomeIntent {
    object FetchUsers : HomeIntent()
    object DefaultScreen : HomeIntent()
    data class OnUserSelected(val selectedUser: User) : HomeIntent()
}