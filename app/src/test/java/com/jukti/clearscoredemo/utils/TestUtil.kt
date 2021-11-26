package com.jukti.clearscoredemo.utils

import com.jukti.clearscoredemo.data.model.ResponseWrapper
import com.jukti.clearscoredemo.domain.model.CoachingSummaryDomainModel
import com.jukti.clearscoredemo.domain.model.CreditReportInfoDomainModel
import com.jukti.clearscoredemo.domain.model.ScoreDomainModel


object TestUtil {

    fun createResponseWrapper(): ResponseWrapper<ScoreDomainModel> {
        return ResponseWrapper.Success(createTestScoreResponse())
    }

    fun createTestScoreResponse() : ScoreDomainModel{
        return ScoreDomainModel("PASS",null, createTestCoachingSummaryDomainModel(),
            createTestCreditReportInfo(),"PASS","INEXPERIENCED")
    }

    fun createTestCreditReportInfo(): CreditReportInfoDomainModel{
        return CreditReportInfoDomainModel(0,
            0,"ABCDEF",0,10,
            10,0,0,100,
            2,23,"Excelent",true,false,
            100,0,2,2,12,0,
        70,2,""
        )
    }

    fun createTestCoachingSummaryDomainModel(): CoachingSummaryDomainModel {
        return CoachingSummaryDomainModel(true,true,9,10,true)
    }

}