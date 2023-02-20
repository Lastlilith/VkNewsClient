package com.imnidasoftware.vknewsclient.ui.theme

import com.imnidasoftware.vknewsclient.domain.FeedPost

sealed class NewsFeedScreenState {

    object Initial: NewsFeedScreenState()

    data class Posts(val posts: List<FeedPost>): NewsFeedScreenState()

}
