package com.jukti.clearscoredemo.data.model

import com.jukti.clearscoredemo.domain.model.CoachingSummaryDomainModel
import com.jukti.clearscoredemo.domain.model.CreditReportInfoDomainModel
import com.jukti.clearscoredemo.domain.model.ScoreDomainModel

fun Score.toScoreDomain() = ScoreDomainModel(
    accountIDVStatus = accountIDVStatus,
    augmentedCreditScore = augmentedCreditScore,
    coachingSummaryDomainModel = coachingSummary?.toCoachingSummaryDomainModel(),
    creditReportInfoDomainModel = creditReportInfo?.toCreditReportInfoDomainModel(),
    dashboardStatus = dashboardStatus,
    personaType = personaType
)

fun CreditReportInfo.toCreditReportInfoDomainModel() = CreditReportInfoDomainModel(
    changeInShortTermDebt = changeInShortTermDebt,
    changedScore = changedScore,
    clientRef = clientRef,
    currentLongTermDebt = currentLongTermDebt,
    currentLongTermNonPromotionalDebt = currentLongTermNonPromotionalDebt,
    currentShortTermCreditLimit = currentShortTermCreditLimit,
    currentShortTermCreditUtilisation= currentShortTermCreditUtilisation,
    currentShortTermDebt = currentShortTermDebt,
    currentShortTermNonPromotionalDebt = currentShortTermNonPromotionalDebt,
    daysUntilNextReport = daysUntilNextReport,
    equifaxScoreBand = equifaxScoreBand,
    equifaxScoreBandDescription = equifaxScoreBandDescription,
    hasEverBeenDelinquent = hasEverBeenDelinquent,
    hasEverDefaulted = hasEverDefaulted,
    maxScoreValue = maxScoreValue,
    minScoreValue = minScoreValue,
    numNegativeScoreFactors = numNegativeScoreFactors,
    numPositiveScoreFactors = numPositiveScoreFactors,
    percentageCreditUsed = percentageCreditUsed,
    percentageCreditUsedDirectionFlag = percentageCreditUsedDirectionFlag,
    score = score,
    scoreBand = scoreBand,
    status = status
)

fun CoachingSummary.toCoachingSummaryDomainModel() = CoachingSummaryDomainModel(
    activeChat = activeChat,
    activeTodo = activeTodo,
    numberOfCompletedTodoItems = numberOfCompletedTodoItems,
    numberOfTodoItems = numberOfTodoItems,
    selected = selected
)