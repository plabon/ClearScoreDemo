package com.jukti.clearscoredemo.domain.creditscore

import com.jukti.clearscoredemo.data.model.ResponseWrapper
import com.jukti.clearscoredemo.data.model.toScoreDomain
import com.jukti.clearscoredemo.domain.ClearScoreRepository
import com.jukti.clearscoredemo.domain.model.ScoreDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCreditScoreUseCase @Inject constructor(private val repository : ClearScoreRepository) {
    suspend fun execute():Flow<ResponseWrapper<ScoreDomainModel>>{
        return repository.getCreditScore().map {
            when(it){
                is ResponseWrapper.Success -> {
                    ResponseWrapper.Success(it.value.toScoreDomain())
                }
                is ResponseWrapper.GenericError -> {
                    ResponseWrapper.GenericError(it.code,it.error)
                }
                is ResponseWrapper.NetworkError -> {
                    ResponseWrapper.NetworkError
                }
            }
        }
    }
}