package com.service.mvisample.commonutils.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.service.mvisample.model.repository.api.MVIRepositoryImpl
import com.service.mvisample.view.viewmodel.SharedViewModel
import retrofit2.Retrofit


class ViewModelFactory(private val retrofit: Retrofit) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(SharedViewModel::class.java)) {
            return SharedViewModel(retrofit) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}