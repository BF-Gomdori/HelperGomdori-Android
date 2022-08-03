package com.bf.helpergomdori.model

sealed class DataState {
    object Inactive: DataState()
    object Loading: DataState()
    data class ResponseData(val data: Data) : DataState()
    data class Error(val error: String) : DataState()
}
