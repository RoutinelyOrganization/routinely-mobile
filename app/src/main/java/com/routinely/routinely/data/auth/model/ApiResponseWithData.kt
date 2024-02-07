package com.routinely.routinely.data.auth.model

sealed class ApiResponseWithData {
    data class Success(val data: Any) : ApiResponseWithData()
    data object Error : ApiResponseWithData()
    data object DefaultError : ApiResponseWithData()
    data object Loading : ApiResponseWithData()
    data object EmptyData : ApiResponseWithData()
    data object Default : ApiResponseWithData()
}