package com.example.ygocarddex.data.api

import com.example.ygocarddex.data.models.CardResponse
import retrofit2.http.GET

interface YugiohApi {
    @GET("cardinfo.php")
    suspend fun getCards(): CardResponse
}