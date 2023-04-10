package com.imnidasoftware.vknewsclient.presentation

import android.app.Application
import com.imnidasoftware.vknewsclient.di.ApplicationComponent
import com.imnidasoftware.vknewsclient.di.DaggerApplicationComponent

class NewsFeedApplication : Application() {

    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}