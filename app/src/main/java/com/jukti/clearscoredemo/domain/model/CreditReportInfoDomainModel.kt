package com.jukti.clearscoredemo.domain.model

import android.os.Parcelable
import com.jukti.clearscoredemo.data.model.CreditReportInfo
import kotlinx.parcelize.Parcelize

@Parcelize
data class CreditReportInfoDomainModel(
    val changeInShortTermDebt: Int?,
    val changedScore: Int?,
    val clientRef: String?,
    val currentLongTermDebt: Int?,
    val currentLongTermNonPromotionalDebt: Int?,
    val currentShortTermCreditLimit: Int?,
    val currentShortTermCreditUtilisation: Int?,
    val currentShortTermDebt: Int?,
    val currentShortTermNonPromotionalDebt: Int?,
    val daysUntilNextReport: Int?,
    val equifaxScoreBand: Int?,
    val equifaxScoreBandDescription: String?,
    val hasEverBeenDelinquent: Boolean?,
    val hasEverDefaulted: Boolean?,
    val maxScoreValue: Int?,
    val minScoreValue: Int?,
    val numNegativeScoreFactors: Int?,
    val numPositiveScoreFactors: Int?,
    val percentageCreditUsed: Int?,
    val percentageCreditUsedDirectionFlag: Int?,
    val score: Int?,
    val scoreBand: Int?,
    val status: String?
): Parcelable {
    var progressPercenrage : Int = 0

    override fun toString(): String {
        return "CreditReportInfoDomainModel(changeInShortTermDebt=$changeInShortTermDebt, changedScore=$changedScore, clientRef='$clientRef',  currentLongTermDebt=$currentLongTermDebt, currentLongTermNonPromotionalDebt=$currentLongTermNonPromotionalDebt, currentShortTermCreditLimit=$currentShortTermCreditLimit, currentShortTermCreditUtilisation=$currentShortTermCreditUtilisation, currentShortTermDebt=$currentShortTermDebt, currentShortTermNonPromotionalDebt=$currentShortTermNonPromotionalDebt, daysUntilNextReport=$daysUntilNextReport, equifaxScoreBand=$equifaxScoreBand, equifaxScoreBandDescription='$equifaxScoreBandDescription', hasEverBeenDelinquent=$hasEverBeenDelinquent, hasEverDefaulted=$hasEverDefaulted, maxScoreValue=$maxScoreValue, minScoreValue=$minScoreValue, numNegativeScoreFactors=$numNegativeScoreFactors, numPositiveScoreFactors=$numPositiveScoreFactors, percentageCreditUsed=$percentageCreditUsed, percentageCreditUsedDirectionFlag=$percentageCreditUsedDirectionFlag, score=$score, scoreBand=$scoreBand, status='$status')"
    }
}

