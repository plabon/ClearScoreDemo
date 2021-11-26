package com.jukti.clearscoredemo.data.model

import com.jukti.clearscoredemo.domain.model.CreditReportInfoDomainModel
import com.jukti.clearscoredemo.domain.model.ScoreDomainModel

data class Score(
    val accountIDVStatus: String?,
    val augmentedCreditScore: Int?,
    val coachingSummary: CoachingSummary?,
    val creditReportInfo: CreditReportInfo?,
    val dashboardStatus: String?,
    val personaType: String?
) {
    override fun toString(): String {
        return "Score(accountIDVStatus='$accountIDVStatus', augmentedCreditScore=$augmentedCreditScore, coachingSummary=$coachingSummary, creditReportInfo=$creditReportInfo, dashboardStatus='$dashboardStatus', personaType='$personaType')"
    }
}




