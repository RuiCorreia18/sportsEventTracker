package com.example.sportseventtracker.data

import retrofit2.http.GET

interface SportsApi {

     @GET("sports")
     suspend fun getSports(): List<SportResponse>
}
