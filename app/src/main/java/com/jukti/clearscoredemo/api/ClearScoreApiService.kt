package com.jukti.clearscoredemo.api

import com.jukti.clearscoredemo.data.model.Score
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ClearScoreApiService {

        @GET("/endpoint.json")
        suspend fun getCreditScore() : Score

}