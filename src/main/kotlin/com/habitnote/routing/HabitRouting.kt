package com.habitnote.routing

import com.habitnote.models.Habit
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

val habit = Habit(
    title = "HabitTitle",
    description = "HabitDescription",
    priority = 0,
    type = 0,
    frequency = 1,
    count = 1,
    color = -22272,
    date = 1667075334008,
    doneDates = listOf(),
    uid = UUID.randomUUID().toString(),
)

fun Application.configureHabitRouting() {
    routing {
        get("/habit") {
            call.respond(listOf(habit))
        }
    }
}