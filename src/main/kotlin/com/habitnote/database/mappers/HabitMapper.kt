package com.habitnote.database.mappers

object HabitMapper {
    fun toDoneDates(doneDates: List<Long>): String {
        return doneDates.joinToString(",") { it.toString() }
    }

    fun fromDoneDates(doneDates: String): List<Long> {
        return if (doneDates.isBlank()) {
            listOf()
        } else {
            doneDates.split(",").map { it.toLong() }
        }
    }
}