package com.stockanalysis.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application class for Stock Analysis App
 * Initializes Hilt dependency injection
 */
@HiltAndroidApp
class StockAnalysisApp : Application() {

    override fun onCreate() {
        super.onCreate()
        // Initialize any app-level configurations here
    }
}
