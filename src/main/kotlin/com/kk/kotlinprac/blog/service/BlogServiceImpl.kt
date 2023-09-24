package com.kk.kotlinprac.blog.service

import com.kk.kotlinprac.blog.dto.BlogDto
import org.springframework.stereotype.Service

@Service
class BlogServiceImpl : BlogService{
    override fun searchKakao(blogDto: BlogDto): String? {

        println(blogDto)

        return "SearchKakao";
    }
}