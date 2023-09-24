package com.kk.kotlinprac.core.response

data class ErrorResponse(
    val message:String,
    val errorType: String = "Invalid Argument",
)
