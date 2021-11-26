package com.jukti.clearscoredemo.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CoachingSummaryDomainModel(
    val activeChat: Boolean?,
    val activeTodo: Boolean?,
    val numberOfCompletedTodoItems: Int?,
    val numberOfTodoItems: Int?,
    val selected: Boolean?
): Parcelable
