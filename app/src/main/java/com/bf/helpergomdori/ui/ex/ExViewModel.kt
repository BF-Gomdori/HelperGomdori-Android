package com.bf.helpergomdori.ui.ex

import androidx.lifecycle.viewModelScope
import com.bf.helpergomdori.base.BaseViewModel
import com.bf.helpergomdori.data.repository.LoginRepository
import com.bf.helpergomdori.model.ex.DataIntent
import com.bf.helpergomdori.model.ex.DataState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class ExViewModel(private val repository: LoginRepository) : BaseViewModel() {
    //todo data 관련 작업
    val dataIntent = Channel<DataIntent>(Channel.UNLIMITED)
    val dataState = MutableStateFlow<DataState>(DataState.Inactive)

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            dataIntent.consumeAsFlow().collect {
                when (it) {
                    is DataIntent.FetchData -> fetchData()
                }
            }
        }
    }

    private fun fetchData() {
        viewModelScope.launch {
            dataState.value = DataState.Loading
            try {
                repository.getData().collect {
                    dataState.value = DataState.ResponseData(it)
                }
            } catch (e: Exception) {
                DataState.Error(e.localizedMessage as String)
            }
        }
    }

}