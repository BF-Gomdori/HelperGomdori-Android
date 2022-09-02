package com.bf.helpergomdori.model.ex

import com.bf.helpergomdori.model.Data

sealed class DataState {
    object Inactive: DataState()
    object Loading: DataState()
    data class ResponseData(val data: Data) : DataState()
    data class Error(val error: String) : DataState()
}
