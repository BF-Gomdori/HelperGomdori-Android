package com.bf.helpergomdori.model

sealed class DataIntent {
    object FetchData : DataIntent()
}