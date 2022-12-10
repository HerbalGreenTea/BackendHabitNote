package com.habitnote

import com.habitnote.database.dao.DatabaseFactory
import com.habitnote.plugins.configureSerialization
import com.habitnote.routing.configureHabitRouting
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module).start(wait = true)
}

fun Application.module() {
    DatabaseFactory.init()
    configureHabitRouting()
    configureSerialization()
}
