package com.imnidasoftware.vknewsclient.domain.usecase

import com.imnidasoftware.vknewsclient.domain.entity.AuthState
import com.imnidasoftware.vknewsclient.domain.repository.NewsFeedRepository
import kotlinx.coroutines.flow.StateFlow

class GetAuthStateFlowUseCase(
    private val repository: NewsFeedRepository
) {
    operator fun invoke(): StateFlow<AuthState> {
        return repository.getAuthStateFlow()
    }
}