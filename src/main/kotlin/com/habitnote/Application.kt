package com.habitnote

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.habitnote.plugins.*
import com.habitnote.routing.configureHabitRouting

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureHabitRouting()
    configureSerialization()
}
