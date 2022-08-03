package com.bf.helpergomdori.ui.main

import androidx.lifecycle.viewModelScope
import com.bf.helpergomdori.base.BaseViewModel
import com.bf.helpergomdori.data.repository.RemoteRepository
import com.bf.helpergomdori.model.DataIntent
import com.bf.helpergomdori.model.DataState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: RemoteRepository): BaseViewModel() {
    //todo data 관련 작업
    val dataIntent = Channel<DataIntent>(Channel.UNLIMITED)
    val dataState = MutableStateFlow<DataState>(DataState.Inactive)

    init {
        handleIntent()
    }

    private fun handleIntent(){
        viewModelScope.launch {
            dataIntent.consumeAsFlow().collect{
                when(it) {
                    is DataIntent.FetchData -> fetchData()
                }
            }
        }
    }

    private fun fetchData() {
        viewModelScope.launch {
            dataState.value = DataState.Loading
            dataState.value = try{
                DataState.ResponseData(repository.getData())
            } catch (e: Exception) {
                DataState.Error(e.localizedMessage)
            }
        }
    }

}