package com.imnidasoftware.vknewsclient.domain.usecase

import com.imnidasoftware.vknewsclient.domain.repository.NewsFeedRepository

class LoadNextDataUseCase(
    private val repository: NewsFeedRepository
) {
    suspend operator fun invoke() {
        repository.loadNextData()
    }
}