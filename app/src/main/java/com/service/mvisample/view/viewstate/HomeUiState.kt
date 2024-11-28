package com.service.mvisample.view.viewstate

import com.service.mvisample.model.User


data class HomeUiState(val loading: Boolean? = false,
                       var users: List<User>? = emptyList(),
                       var selectedUser: User? = null,
                       val defaultScreen: String? = null,
                       val errorMessage: String? = null)