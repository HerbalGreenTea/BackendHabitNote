package com.habitnote.models

import com.habitnote.serializers.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class HabitUUID(
    @Serializable(with = UUIDSerializer::class)
    val uid: UUID
)
