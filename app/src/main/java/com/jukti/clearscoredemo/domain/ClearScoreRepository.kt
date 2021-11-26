package com.jukti.clearscoredemo.domain

import androidx.lifecycle.LiveData
import com.jukti.clearscoredemo.data.model.ResponseWrapper
import com.jukti.clearscoredemo.data.model.Score
import kotlinx.coroutines.flow.Flow

interface ClearScoreRepository {
    suspend fun getCreditScore(): Flow<ResponseWrapper<Score>>
    }