package com.jukti.clearscoredemo.data.repository

import android.util.Log
import com.jukti.clearscoredemo.data.model.ResponseWrapper
import com.jukti.clearscoredemo.data.model.Score
import com.jukti.clearscoredemo.data.remote.ClearScoreDataSource
import com.jukti.clearscoredemo.domain.ClearScoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton



@Singleton
class ClearScoreRepositoryImp @Inject constructor(private val remoteDataSource: ClearScoreDataSource):ClearScoreRepository{

    override suspend fun getCreditScore(): Flow<ResponseWrapper<Score>> {
        return flow {
            Log.e(TAG, "I'm working in thread ${Thread.currentThread().name}")
            emit(remoteDataSource.getCreditScore())
        }
    }


    companion object{
        private const val TAG = "ClearScoreRepository"
    }

}