package com.kk.kotlinprac.blog.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.kk.kotlinprac.core.annotation.ValidEnum
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

/*
    data <-
 */
data class BlogDto(
    @field:NotBlank(message = "query parameter required")
    @JsonProperty("query")
    private val _query:String?,



    @field:NotBlank(message = "sort parameter required")
    @field:ValidEnum(enumClass = EnumSort::class, message = "sort parameter on of ACCURACY or RECENCY")
    val sort : String?,

    @field:NotNull(message = "page parameter required")
    @field:Max(50, message = "page is more than max")
    @field:Min(1, message = "page is less than max")
    val page : Int?,

    @field:NotNull(message = "size parameter required")
    val size:Int?
){
    val query:String
        get() = _query!!

    private enum class EnumSort{
        ACCURACY,
        RECENCY
    }
}
