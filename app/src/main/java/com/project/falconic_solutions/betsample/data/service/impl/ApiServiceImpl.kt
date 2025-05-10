package com.project.falconic_solutions.betsample.data.service.impl

import com.project.falconic_solutions.betsample.data.model.response.Event
import com.project.falconic_solutions.betsample.data.service.ApiService
import retrofit2.HttpException
import java.io.IOException

class ApiServiceImpl(private val apiService: ApiService) {

    suspend fun getEvents(): Result<List<Event>> {
        return try {
            val response = apiService.getEvents()
            Result.success(response)
        } catch (e: HttpException) {
            Result.failure(e)
        } catch (e: IOException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getEventOdds(id: String): Result<Event?> {
        return try {
            val response = apiService.getEventOdds(eventId = id)
            Result.success(response)
        } catch (e: HttpException) {
            Result.failure(e)
        } catch (e: IOException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}