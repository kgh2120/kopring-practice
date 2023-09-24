package com.kk.kotlinprac.blog.dto

/*
    data <-
 */
data class BlogDto(
    val query:String,
    val sort : String,
    val page : Int,
    val size:Int
)
