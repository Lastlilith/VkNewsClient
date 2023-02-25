package com.imnidasoftware.vknewsclient.presentation.news

import com.imnidasoftware.vknewsclient.domain.FeedPost

sealed class NewsFeedScreenState {

    object Initial: NewsFeedScreenState()

    data class Posts(val posts: List<FeedPost>): NewsFeedScreenState()

}
