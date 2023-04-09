package com.imnidasoftware.vknewsclient.domain.usecase

import com.imnidasoftware.vknewsclient.domain.entity.FeedPost
import com.imnidasoftware.vknewsclient.domain.repository.NewsFeedRepository

class DeletePostUseCase(
    private val repository: NewsFeedRepository
) {
    suspend operator fun invoke(feedPost: FeedPost) {
        repository.deletePost(feedPost)
    }
}