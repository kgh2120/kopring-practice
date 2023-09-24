package com.kk.kotlinprac.blog.service

import com.kk.kotlinprac.blog.dto.BlogDto

interface BlogService {

    fun searchKakao(blogDto: BlogDto):String?
}