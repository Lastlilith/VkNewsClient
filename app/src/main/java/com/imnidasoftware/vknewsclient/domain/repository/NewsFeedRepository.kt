package com.imnidasoftware.vknewsclient.domain.repository

import com.imnidasoftware.vknewsclient.domain.entity.AuthState
import com.imnidasoftware.vknewsclient.domain.entity.FeedPost
import com.imnidasoftware.vknewsclient.domain.entity.PostComment
import kotlinx.coroutines.flow.StateFlow

interface NewsFeedRepository {

    fun getAuthStateFlow(): StateFlow<AuthState>

    fun getRecommendations(): StateFlow<List<FeedPost>>

    fun getComments(feedPost: FeedPost): StateFlow<List<PostComment>>

    suspend fun loadNextData()

    suspend fun checkAuthState()

    suspend fun deletePost(feedPost: FeedPost)

    suspend fun changeLikeStatus(feedPost: FeedPost)
}