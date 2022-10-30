package com.habitnote

import com.habitnote.models.Habit
import com.habitnote.models.HabitUUID
import java.util.*

object InMemoryCache {
    private val firstUUID = UUID.randomUUID().toString()

    private val habit = Habit(
        title = "HabitTitle",
        description = "HabitDescription",
        priority = 0,
        type = 0,
        frequency = 1,
        count = 1,
        color = -22272,
        date = 1667075334008,
        doneDates = listOf(),
        uid = firstUUID,
    )

    private val habits = mutableMapOf<String, Habit>(firstUUID to habit)

    fun getHabits(): List<Habit> = habits.values.toList()

    fun addHabit(habit: Habit): HabitUUID {
        val habitUUID = getHabitUUID()
        val habitWithUUID = habit.copy(
            uid = habitUUID.uid
        )

        habits[habitUUID.uid] = habitWithUUID

        return habitUUID
    }

    private fun getHabitUUID(): HabitUUID {
        val uuid = UUID.randomUUID().toString()
        return HabitUUID(uuid)
    }

    fun isContainsHabit(habit: Habit): Boolean {
        return habits.containsKey(habit.uid)
    }
}