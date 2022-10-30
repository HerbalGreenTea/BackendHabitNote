package com.habitnote.models

import kotlinx.serialization.Serializable

@Serializable
data class HabitDone(
    val habit_uid: String,
    val date: Long,
)
