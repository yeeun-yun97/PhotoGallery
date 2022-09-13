package com.github.yeeun_yun97.toy.photogallary.data

import com.github.yeeun_yun97.toy.photogallary.secret.APP_KEY
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HttpRequestHelper {
    private val client = HttpClient(CIO)

    suspend fun requestTest(): String = withContext(Dispatchers.IO) {
        val url = "https://dapi.kakao.com/v2/search/image"
        val response: HttpResponse = client.get(url){
            url{
                parameters.append("query","강아지")
                parameters.append("page","1")
                parameters.append("size","40")
            }
            method = HttpMethod.Get
            headers{
                append("Authorization", APP_KEY)
            }
        }
        val responseStatus = response.status

        if(responseStatus == HttpStatusCode.OK){
            response.readText()
        }else{
            "error: $responseStatus"
        }
    }
}