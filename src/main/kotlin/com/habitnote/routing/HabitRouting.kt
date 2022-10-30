package com.habitnote.routing

import com.habitnote.InMemoryCache
import com.habitnote.models.Habit
import com.habitnote.models.HabitDone
import com.habitnote.models.HabitUUID
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureHabitRouting() {
    routing {
        get("/habit") {
            call.respond(InMemoryCache.getHabits())
        }

        put("/habit") {
            val habit = call.receive(Habit::class)

            if (InMemoryCache.isContainsHabit(habit)) {
                call.respond(HttpStatusCode.Conflict, "данная привычка уже содержится на сервере")
            }

            val habitUUID = InMemoryCache.addHabit(habit)

            call.respond(habitUUID)
        }

        post("/habit_done") {
            val habitDone = call.receive(HabitDone::class)
            val isHabitDone = InMemoryCache.doneHabit(habitDone)

            if (isHabitDone) {
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.NotFound, "привычка не найдена")
            }
        }

        delete("/habit") {
            val habitUUID = call.receive(HabitUUID::class)

            InMemoryCache.deleteHabit(habitUUID)

            call.respond(HttpStatusCode.OK)
        }
    }
}