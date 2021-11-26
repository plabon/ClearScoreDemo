package com.jukti.clearscoredemo.domain.model

import android.os.Parcelable
import com.jukti.clearscoredemo.data.model.Score
import kotlinx.parcelize.Parcelize

@Parcelize
data class ScoreDomainModel(
    val accountIDVStatus: String?,
    val augmentedCreditScore: Int?,
    val coachingSummaryDomainModel: CoachingSummaryDomainModel?,
    val creditReportInfoDomainModel: CreditReportInfoDomainModel?,
    val dashboardStatus: String?,
    val personaType: String?
): Parcelable {

    override fun toString(): String {
        return "Score(accountIDVStatus='$accountIDVStatus', augmentedCreditScore=$augmentedCreditScore, coachingSummary=$coachingSummaryDomainModel, creditReportInfo=$creditReportInfoDomainModel, dashboardStatus='$dashboardStatus', personaType='$personaType')"
    }
}





