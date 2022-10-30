package com.habitnote.routing

import com.habitnote.InMemoryCache
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureHabitRouting() {
    routing {
        get("/habit") {
            call.respond(InMemoryCache.getHabits())
        }
    }
}