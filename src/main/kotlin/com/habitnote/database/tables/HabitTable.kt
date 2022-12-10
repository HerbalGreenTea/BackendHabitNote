package com.habitnote.database.tables

import org.jetbrains.exposed.sql.Table

object HabitTable : Table() {
    val title = varchar("title", 128)
    val description = varchar("description", 128)
    val priority = integer("priority")
    val type = integer("type")
    val frequency = integer("frequency")
    val count = integer("count")
    val color = integer("color")
    val date = long("date")
    val done_dates = varchar("done_dates", 1000000000)
    val uid = varchar("uid", 36)

    override val primaryKey = PrimaryKey(uid)
}