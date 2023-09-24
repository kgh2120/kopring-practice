package com.kk.kotlinprac.blog.service

import com.kk.kotlinprac.blog.dto.BlogDto
import com.kk.kotlinprac.blog.entity.Wordcount

interface BlogService {

    fun searchKakao(blogDto: BlogDto):String?

    fun searchWordRank():List<Wordcount>
}