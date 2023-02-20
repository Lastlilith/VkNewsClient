package com.imnidasoftware.vknewsclient.ui.theme

import com.imnidasoftware.vknewsclient.domain.FeedPost
import com.imnidasoftware.vknewsclient.domain.PostComment

sealed class HomeScreenState {

    object Initial: HomeScreenState()

    data class Posts(val posts: List<FeedPost>): HomeScreenState()

    data class Comments(val feedPost: FeedPost, val comments: List<PostComment>): HomeScreenState()

}
