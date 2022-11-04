package com.habitnote.models

import com.habitnote.serializers.HabitUUIDSerializer
import kotlinx.serialization.Serializable

@Serializable
data class HabitDone(
    @Serializable(with = HabitUUIDSerializer::class)
    val habit_uid: HabitUUID,
    val date: Long,
)
