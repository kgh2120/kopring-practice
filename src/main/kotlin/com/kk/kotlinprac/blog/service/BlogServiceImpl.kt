package com.kk.kotlinprac.blog.service

import com.kk.kotlinprac.blog.dto.BlogDto
import com.kk.kotlinprac.core.exception.InvalidInputException
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono


@Service
class BlogServiceImpl : BlogService {

    @Value("\${key.kakao}")
    lateinit var key: String

    override fun searchKakao(blogDto: BlogDto): String? {
        val msgList = mutableListOf<ExceptionMsg>()

        if(blogDto.query.trim().isEmpty())
            msgList.add(ExceptionMsg.EMPTY_QUERY)
        if(blogDto.sort.trim() !in arrayOf("accuracy","recency"))
            msgList.add(ExceptionMsg.NOT_IN_SORT)

        when{
            blogDto.page < 1 -> msgList.add(ExceptionMsg.LESS_THAN_MIN)
            blogDto.page > 50 -> msgList.add(ExceptionMsg.MORE_THAN_MAX)
        }

        if (msgList.isNotEmpty()) {
            val message = msgList.joinToString { it.msg }
            throw InvalidInputException(message)
        }



        val webClient = WebClient
            .builder()
            .baseUrl("https://dapi.kakao.com")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

        val response = webClient
            .get()
            .uri {
                it.path("/v2/search/blog")
                    .queryParam("query", blogDto.query)
                    .queryParam("sort", blogDto.sort)
                    .queryParam("page", blogDto.page)
                    .queryParam("size", blogDto.size)
                    .build()
            }
            .header(HttpHeaders.AUTHORIZATION, "KakaoAK ${key}")
            .retrieve()
            .bodyToMono<String>();


        return response.block();
    }
}


private enum class ExceptionMsg(val msg:String){
    EMPTY_QUERY("query parameter required"),
    NOT_IN_SORT("sort parameter one of accuracy and recency"),
    LESS_THAN_MIN("page is less than min"),
    MORE_THAN_MAX("page is more than max"),
}