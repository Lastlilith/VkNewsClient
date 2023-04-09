package com.imnidasoftware.vknewsclient.presentation.news

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.imnidasoftware.vknewsclient.data.repository.NewsFeedRepositoryImpl
import com.imnidasoftware.vknewsclient.domain.entity.FeedPost
import com.imnidasoftware.vknewsclient.domain.usecase.ChangeLikeStatusUseCase
import com.imnidasoftware.vknewsclient.domain.usecase.DeletePostUseCase
import com.imnidasoftware.vknewsclient.domain.usecase.GetRecommendationsUseCase
import com.imnidasoftware.vknewsclient.domain.usecase.LoadNextDataUseCase
import com.imnidasoftware.vknewsclient.extensions.mergeWith
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NewsFeedViewModel(application: Application) : AndroidViewModel(application) {

    private val exceptionHandler = CoroutineExceptionHandler { _, _->
        Log.e("POPO", "Exception caught by exception handler" )
    }

    private val repository = NewsFeedRepositoryImpl(application)

    private val getRecommendationsUseCase = GetRecommendationsUseCase(repository)
    private val loadNextDataUseCase = LoadNextDataUseCase(repository)
    private val changeLikeStatusUseCase = ChangeLikeStatusUseCase(repository)
    private val deletePostUseCase = DeletePostUseCase(repository)

    private val recommendationsFlow = getRecommendationsUseCase()

    private val loadNextDataFlow = MutableSharedFlow<NewsFeedScreenState>()


    val screenState = recommendationsFlow
        .filter { it.isNotEmpty() }
        .map { NewsFeedScreenState.Posts(posts = it) as NewsFeedScreenState }
        .onStart { emit(NewsFeedScreenState.Loading) }
        .mergeWith(loadNextDataFlow)


    fun loadNextRecommendations() {
        viewModelScope.launch {
            loadNextDataFlow.emit(
                NewsFeedScreenState.Posts(
                    posts = recommendationsFlow.value,
                    nextDataIsLoading = true
                )
            )
            loadNextDataUseCase()
        }
    }

    fun changeLikeStatus(feedPost: FeedPost) {
        viewModelScope.launch(exceptionHandler) {
            changeLikeStatusUseCase(feedPost)
        }
    }


    fun remove(feedPost: FeedPost) {
        viewModelScope.launch(exceptionHandler) {
            deletePostUseCase(feedPost)
        }

    }
}