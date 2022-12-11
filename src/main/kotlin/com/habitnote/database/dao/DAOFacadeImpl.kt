package com.habitnote.database.dao

import com.habitnote.database.dao.DatabaseFactory.dbQuery
import com.habitnote.database.tables.HabitTable
import com.habitnote.models.Habit
import com.habitnote.models.HabitUUID
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.util.*

class DAOFacadeImpl : DAOFacade {
    override suspend fun allHabits(): List<Habit> = dbQuery {
        HabitTable.selectAll().map(::resultRowToHabit)
    }

    override suspend fun getHabit(uid: HabitUUID): Habit? = dbQuery {
        HabitTable
            .select { HabitTable.uid eq uid.uid.toString() }
            .map(::resultRowToHabit)
            .singleOrNull()
    }

    override suspend fun addHabit(
        title: String,
        description: String,
        priority: Int,
        type: Int,
        frequency: Int,
        count: Int,
        color: Int,
        date: Long,
        done_dates: List<Long>,
    ): Habit? = dbQuery {
        val insertStatement = HabitTable.insert {
            it[HabitTable.title] = title
            it[HabitTable.description] = description
            it[HabitTable.priority] = priority
            it[HabitTable.type] = type
            it[HabitTable.frequency] = frequency
            it[HabitTable.count] = count
            it[HabitTable.color] = color
            it[HabitTable.date] = date
            it[HabitTable.done_dates] = toDoneDates(done_dates)
            it[HabitTable.uid] = UUID.randomUUID().toString()
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToHabit)
    }

    override suspend fun updateHabit(
        title: String,
        description: String,
        priority: Int,
        type: Int,
        frequency: Int,
        count: Int,
        color: Int,
        date: Long,
        done_dates: List<Long>,
        uid: HabitUUID
    ): Boolean = dbQuery {
        HabitTable.update({ HabitTable.uid eq uid.uid.toString() }) {
            it[HabitTable.title] = title
            it[HabitTable.description] = description
            it[HabitTable.priority] = priority
            it[HabitTable.type] = type
            it[HabitTable.frequency] = frequency
            it[HabitTable.count] = count
            it[HabitTable.color] = color
            it[HabitTable.date] = date
            it[HabitTable.done_dates] = toDoneDates(done_dates)
        } > 0
    }

    override suspend fun deleteHabit(uid: HabitUUID): Boolean = dbQuery {
        HabitTable.deleteWhere { HabitTable.uid eq uid.uid.toString() } > 0
    }

    private fun resultRowToHabit(row: ResultRow) = Habit(
        title = row[HabitTable.title],
        description = row[HabitTable.description],
        priority = row[HabitTable.priority],
        type = row[HabitTable.type],
        frequency = row[HabitTable.frequency],
        count = row[HabitTable.count],
        color = row[HabitTable.color],
        date = row[HabitTable.date],
        done_dates = fromDoneDates(row[HabitTable.done_dates]),
        uid = HabitUUID(UUID.fromString(row[HabitTable.uid])),
    )

    private fun toDoneDates(doneDates: List<Long>): String {
        return doneDates.joinToString(",") { it.toString() }
    }

    private fun fromDoneDates(doneDates: String): List<Long> {
        return if (doneDates.isBlank()) {
            listOf()
        } else {
            doneDates.split(",").map { it.toLong() }
        }
    }

    companion object {
        fun getHabitDao(): DAOFacade {
            return DAOFacadeImpl()
        }
    }
}