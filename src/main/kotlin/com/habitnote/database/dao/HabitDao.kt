package com.habitnote.database.dao

import com.habitnote.models.Habit
import com.habitnote.models.HabitUUID

interface HabitDao {
    suspend fun allHabits(): List<Habit>
    suspend fun getHabit(uid: HabitUUID): Habit?
    suspend fun addHabit(habit: Habit): Habit?
    suspend fun updateHabit(habit: Habit): Boolean
    suspend fun deleteHabit(uid: HabitUUID): Boolean
}