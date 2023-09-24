package com.kk.kotlinprac.blog.repository

import com.kk.kotlinprac.blog.entity.Wordcount
import org.springframework.data.jpa.repository.JpaRepository

// extend, implements 하는 방법 -> : 입력
interface WordcountRepository:JpaRepository<Wordcount,String>  {

    fun findTop10ByOrderByCntDesc():List<Wordcount>
}