package com.habitnote.routing

import com.habitnote.database.dao.DAOFacadeImpl
import com.habitnote.models.Habit
import com.habitnote.models.HabitDone
import com.habitnote.models.HabitUUID
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureHabitRouting() {
    val habitDao = DAOFacadeImpl.getHabitDao()

    routing {
        get("/habit") {
            call.respond(habitDao.allHabits())
        }

        put("/habit") {
            val habit = call.receive(Habit::class)

            val addedHabit = habitDao.addHabit(
                title = habit.title,
                description = habit.description,
                priority = habit.priority,
                type = habit.type,
                frequency = habit.frequency,
                count = habit.count,
                color = habit.color,
                date = habit.date,
                done_dates = habit.done_dates,
            )

            if (addedHabit != null) {
                call.respond(addedHabit.uid)
            } else {
                call.respond(HttpStatusCode.Conflict, "данная привычка уже содержится на сервере")
            }
        }

        post("/habit_done") {
            val habitDone = call.receive(HabitDone::class)

            val habit = habitDao.getHabit(habitDone.habit_uid)?.let { habit ->
                val habitDoneDate = mutableListOf<Long>().apply {
                    addAll(habit.done_dates)
                    add(habitDone.date)
                }

                val updatedHabit = habit.copy(
                    done_dates = habitDoneDate
                )

                habitDao.updateHabit(
                    title = updatedHabit.title,
                    description = updatedHabit.description,
                    priority = updatedHabit.priority,
                    type = updatedHabit.type,
                    frequency = updatedHabit.frequency,
                    count = updatedHabit.count,
                    color = updatedHabit.color,
                    date = updatedHabit.date,
                    done_dates = updatedHabit.done_dates,
                    uid = habitDone.habit_uid,
                )
            }

            if (habit != null) {
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.NotFound, "привычка не найдена")
            }
        }

        delete("/habit") {
            val habitUUID = call.receive(HabitUUID::class)

            val isHabitDeleted = habitDao.deleteHabit(habitUUID)

            if (isHabitDeleted) {
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.NotFound, "привычка не найдена")
            }
        }
    }
}