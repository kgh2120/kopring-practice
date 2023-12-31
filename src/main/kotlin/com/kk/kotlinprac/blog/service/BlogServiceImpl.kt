package com.kk.kotlinprac.blog.service

import com.kk.kotlinprac.blog.dto.BlogDto
import com.kk.kotlinprac.blog.entity.Wordcount
import com.kk.kotlinprac.blog.repository.WordcountRepository
import com.kk.kotlinprac.core.exception.InvalidInputException
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono


@Service
class BlogServiceImpl(
    val wordcountRepository: WordcountRepository
) : BlogService {

    @Value("\${key.kakao}")
    lateinit var key: String

    override fun searchKakao(blogDto: BlogDto): String? {




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

        val result =  response.block();

        val lowQuery:String = blogDto.query.lowercase()

        val word:Wordcount = wordcountRepository.findById(lowQuery)
            .orElse(Wordcount(lowQuery))

        word.cnt++;
        wordcountRepository.save(word);


        return result;
    }

    override fun searchWordRank(): List<Wordcount> = wordcountRepository.findTop10ByOrderByCntDesc()
}