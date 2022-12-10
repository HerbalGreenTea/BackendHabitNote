package com.habitnote.database.dao

import com.habitnote.models.Habit
import com.habitnote.models.HabitUUID

interface DAOFacade {
    suspend fun allHabits(): List<Habit>
    suspend fun addHabit(
        title: String,
        description: String,
        priority: Int,
        type: Int,
        frequency: Int,
        count: Int,
        color: Int,
        date: Long,
        done_dates: List<Long>,
        uid: HabitUUID,
    ): Habit?
}