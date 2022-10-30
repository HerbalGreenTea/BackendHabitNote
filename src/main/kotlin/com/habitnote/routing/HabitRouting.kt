package com.habitnote.routing

import com.habitnote.InMemoryCache
import com.habitnote.models.Habit
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
    }
}