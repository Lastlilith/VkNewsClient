package com.imnidasoftware.vknewsclient.data.network

import com.imnidasoftware.vknewsclient.data.model.NewsFeedResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("newsfeed.getRecommended?v=5.131")
    suspend fun loadRecommended(
        @Query("access_token") token: String
    ): NewsFeedResponseDto
}