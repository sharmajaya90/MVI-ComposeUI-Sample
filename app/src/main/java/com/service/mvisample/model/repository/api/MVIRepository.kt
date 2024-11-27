package com.service.mvisample.model.repository.api

import com.service.mvisample.model.User
import kotlinx.coroutines.flow.Flow


interface MVIRepository {
    suspend fun getUsers(): Flow<List<User>>
}
