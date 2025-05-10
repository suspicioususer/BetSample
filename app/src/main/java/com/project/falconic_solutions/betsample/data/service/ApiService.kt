package com.project.falconic_solutions.betsample.data.service

import com.project.falconic_solutions.betsample.BuildConfig
import com.project.falconic_solutions.betsample.data.model.response.Event
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("sports/soccer_turkey_super_league/events")
    suspend fun getEvents(@Query("apiKey") apiKey: String = BuildConfig.BET_API_KEY): List<Event>

    @GET("sports/soccer_turkey_super_league/events/{eventId}/odds")
    suspend fun getEventOdds(@Path("eventId") eventId: String, @Query("regions") regions: String = "eu", @Query("oddsFormat") oddsFormat: String = "decimal", @Query("apiKey") apiKey: String = BuildConfig.BET_API_KEY): Event?

}