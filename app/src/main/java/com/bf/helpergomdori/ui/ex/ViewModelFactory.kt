package com.bf.helpergomdori.ui.ex

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bf.helpergomdori.data.remote.RemoteDataSource
import com.bf.helpergomdori.data.repository.RemoteRepository

class ViewModelFactory(private val remoteDataSource: RemoteDataSource) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExViewModel::class.java)) {
            return ExViewModel(RemoteRepository(remoteDataSource)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}