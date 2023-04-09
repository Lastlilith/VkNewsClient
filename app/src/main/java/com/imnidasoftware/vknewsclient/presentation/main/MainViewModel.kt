package com.imnidasoftware.vknewsclient.presentation.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.imnidasoftware.vknewsclient.data.repository.NewsFeedRepositoryImpl
import com.imnidasoftware.vknewsclient.domain.usecase.CheckAuthStateUseCase
import com.imnidasoftware.vknewsclient.domain.usecase.GetAuthStateFlowUseCase
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = NewsFeedRepositoryImpl(application)

    private val getAuthStateFlowUseCase = GetAuthStateFlowUseCase(repository)
    private val checkAuthStateUseCase = CheckAuthStateUseCase(repository)

    val authState = getAuthStateFlowUseCase()

    fun performAuthResult() {
        viewModelScope.launch {
            checkAuthStateUseCase()
        }
    }
}