package com.imnidasoftware.vknewsclient.data.repository

import android.app.Application
import com.imnidasoftware.vknewsclient.data.mapper.NewsFeedMapper
import com.imnidasoftware.vknewsclient.data.network.ApiFactory
import com.imnidasoftware.vknewsclient.domain.FeedPost
import com.imnidasoftware.vknewsclient.domain.PostComment
import com.imnidasoftware.vknewsclient.domain.StatisticItem
import com.imnidasoftware.vknewsclient.domain.StatisticType
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken

class NewsFeedRepository(application: Application) {

    private val storage = VKPreferencesKeyValueStorage(application)
    private val token = VKAccessToken.restore(storage)

    private val apiService = ApiFactory.apiService
    private val mapper = NewsFeedMapper()

    private val _feedPosts = mutableListOf<FeedPost>()
    val feedPosts: List<FeedPost> = _feedPosts.toList()

    private var nextFrom: String? = null

    suspend fun loadRecommendations(): List<FeedPost> {
        val startFrom = nextFrom

        if (startFrom == null && feedPosts.isNotEmpty()) return feedPosts

        val response = if (startFrom == null) {
            apiService.loadRecommended(getAccessToken())
        } else {
            apiService.loadRecommended(getAccessToken(), startFrom)
        }
        nextFrom = response.newsFeedContentDto.nextFrom
        val posts = mapper.mapResponseToPosts(response)
        _feedPosts.addAll(posts)
        return feedPosts
    }

    private fun getAccessToken(): String {
        return token?.accessToken ?: throw IllegalAccessException("Token is null")
    }

    suspend fun deletePost(feedPost: FeedPost) {
        apiService.ignorePost(
            accessToken = getAccessToken(),
            ownerId = feedPost.communityId,
            postId = feedPost.id
        )
        _feedPosts.remove(feedPost)
    }

    suspend fun getComments(feedPost: FeedPost): List<PostComment> {
        val comments = apiService.getComments(
            accessToken = getAccessToken(),
            ownerId = feedPost.communityId,
            postId = feedPost.id
        )
        return mapper.mapResponseToComments(comments)
    }

    suspend fun changeLikeStatus(feedPost: FeedPost) {
        val response = if (feedPost.isLiked) {
            apiService.deleteLike(
                token = getAccessToken(),
                ownerId = feedPost.communityId,
                postId = feedPost.id
            )
        } else {
            apiService.addLike(
                token = getAccessToken(),
                ownerId = feedPost.communityId,
                postId = feedPost.id
            )
        }
        val newLikesCount = response.likes.count
        val newStatistics = feedPost.statistics.toMutableList().apply {
            removeIf { it.type == StatisticType.LIKES}
            add((StatisticItem(type = StatisticType.LIKES, newLikesCount)))
        }
        val newPost = feedPost.copy(statistics = newStatistics, isLiked = !feedPost.isLiked)
        val postIndex = _feedPosts.indexOf(feedPost)
        _feedPosts[postIndex] = newPost
    }
}