package com.habitnote.models

import com.habitnote.serializers.HabitUUIDSerializer
import kotlinx.serialization.Serializable

@Serializable
data class Habit(
    val title: String,
    val description: String,
    val priority: Int,
    val type: Int,
    val frequency: Int,
    val count: Int,
    val color: Int,
    val date: Long,
    val done_dates: List<Long>,
    @Serializable(with = HabitUUIDSerializer::class)
    val uid: HabitUUID,
)
