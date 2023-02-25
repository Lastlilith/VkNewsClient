package com.imnidasoftware.vknewsclient.presentation.main

sealed class AuthState {

    object Authorized: AuthState()
    object NotAuthorized: AuthState()
    object Initial: AuthState()
}
