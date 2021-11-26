package com.jukti.clearscoredemo.data.remote

import com.jukti.clearscoredemo.api.NoConnectivityException
import com.jukti.clearscoredemo.api.ClearScoreApiService
import com.jukti.clearscoredemo.data.model.ResponseWrapper
import com.jukti.clearscoredemo.data.model.Score
import com.jukti.clearscoredemo.di.IoDispatcher
import com.jukti.unrd.utilities.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

import javax.inject.Inject

class ClearScoreDataSource @Inject constructor(private val apiService: ClearScoreApiService,
                                               @IoDispatcher private val ioDispatcher: CoroutineDispatcher){

    suspend fun getCreditScore(): ResponseWrapper<Score>{
        return safeApiCall(apiCall = { apiService.getCreditScore()})
    }

    suspend fun <T> safeApiCall(apiCall: suspend () -> T): ResponseWrapper<T> {
        return withContext(ioDispatcher) {
            try {
                ResponseWrapper.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is NoConnectivityException -> ResponseWrapper.NetworkError
                    is IOException -> ResponseWrapper.NetworkError
                    is HttpException -> {
                        val code = throwable.code()
                        val msg = throwable.message()
                        val errorMsg = if (msg.isNullOrEmpty()) {
                            throwable.response()?.errorBody()?.toString()
                        } else {
                            msg
                        }
                        ResponseWrapper.GenericError(code, errorMsg)
                    }
                    else -> {
                        ResponseWrapper.GenericError(null, null)
                    }
                }
            }
        }
    }


    companion object {
        private const val TAG = "ClearScoreDataSource"
    }


}