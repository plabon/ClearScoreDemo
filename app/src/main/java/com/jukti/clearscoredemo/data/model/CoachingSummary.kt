package com.jukti.clearscoredemo.data.model

data class CoachingSummary(
    val activeChat: Boolean?,
    val activeTodo: Boolean?,
    val numberOfCompletedTodoItems: Int?,
    val numberOfTodoItems: Int?,
    val selected: Boolean?
)
