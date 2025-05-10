package com.project.falconic_solutions.betsample.ui.viewmodel

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AnalyticsViewModel @Inject constructor(private val analytics: FirebaseAnalytics) : ViewModel() {

    fun logScreenEvent(screenName: String) {
        val bundle = Bundle().apply {
            putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
            putString(FirebaseAnalytics.Param.SCREEN_CLASS, "MainActivity")
        }
        analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
    }

    fun logCartEvent(eventName: String, success: Boolean? = null) {
        val bundle = Bundle().apply {
            putString(FirebaseAnalytics.Param.METHOD, eventName)
            success?.let {
                putString(FirebaseAnalytics.Param.SUCCESS, it.toString())
            }
        }
        analytics.logEvent("cart_event", bundle)
    }

}