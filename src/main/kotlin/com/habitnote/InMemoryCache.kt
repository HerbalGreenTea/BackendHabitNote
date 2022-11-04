package com.habitnote

import com.habitnote.models.Habit
import com.habitnote.models.HabitDone
import com.habitnote.models.HabitUUID
import java.util.*

object InMemoryCache {
    private val habits = mutableMapOf<HabitUUID, Habit>()

    fun getHabits(): List<Habit> = habits.values.toList()

    fun addHabit(habit: Habit): HabitUUID {
        val habitUUID = getHabitUUID()
        val habitWithUUID = habit.copy(
            uid = habitUUID
        )

        habits[habitUUID] = habitWithUUID

        return habitUUID
    }

    fun doneHabit(habitDone: HabitDone): Boolean {
        val habit = habits[habitDone.habit_uid]?.let { habit ->

            val habitDoneDate = mutableListOf<Long>().apply {
                addAll(habit.done_dates)
                add(habitDone.date)
            }

            val doneHabit = habit.copy(
                done_dates = habitDoneDate
            )

            habits[habitDone.habit_uid] = doneHabit
        }

        return habit != null
    }

    fun deleteHabit(habitUUID: HabitUUID) {
        habits.remove(habitUUID)
    }

    private fun getHabitUUID(): HabitUUID {
        val uuid = UUID.randomUUID()
        return HabitUUID(uuid)
    }

    fun isContainsHabit(habit: Habit): Boolean {
        return habits.containsKey(habit.uid)
    }
}