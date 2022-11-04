package com.habitnote.models

import com.habitnote.serializers.HabitUUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

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
    val done_dates: List<Long> = listOf(),
    @Serializable(with = HabitUUIDSerializer::class)
    val uid: HabitUUID = HabitUUID(UUID.randomUUID()),
)
