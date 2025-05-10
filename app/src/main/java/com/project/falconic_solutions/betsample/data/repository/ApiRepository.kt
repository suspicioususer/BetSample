package com.project.falconic_solutions.betsample.data.repository

import com.project.falconic_solutions.betsample.data.model.response.Event
import com.project.falconic_solutions.betsample.data.service.impl.ApiServiceImpl
import javax.inject.Inject

class ApiRepository @Inject constructor(private val apiServiceImpl: ApiServiceImpl) {

    suspend fun getEvents(): Result<List<Event>> {
        return apiServiceImpl.getEvents()
    }

    suspend fun getEventOdds(id: String): Result<Event?> {
        return apiServiceImpl.getEventOdds(id)
    }
}