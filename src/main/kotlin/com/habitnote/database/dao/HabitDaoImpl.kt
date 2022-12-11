package com.habitnote.database.dao

import com.habitnote.database.dao.DatabaseFactory.dbQuery
import com.habitnote.database.mappers.HabitMapper
import com.habitnote.database.tables.HabitTable
import com.habitnote.models.Habit
import com.habitnote.models.HabitUUID
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.util.*

class HabitDaoImpl : HabitDao {
    override suspend fun allHabits(): List<Habit> = dbQuery {
        HabitTable.selectAll().map(::resultRowToHabit)
    }

    override suspend fun getHabit(uid: HabitUUID): Habit? = dbQuery {
        HabitTable
            .select { HabitTable.uid eq uid.uid.toString() }
            .map(::resultRowToHabit)
            .singleOrNull()
    }

    override suspend fun addHabit(habit: Habit): Habit? = dbQuery {
        val insertStatement = HabitTable.insert {
            it[title] = habit.title
            it[description] = habit.description
            it[priority] = habit.priority
            it[type] = habit.type
            it[frequency] = habit.frequency
            it[count] = habit.count
            it[color] = habit.color
            it[date] = habit.date
            it[done_dates] = HabitMapper.toDoneDates(habit.done_dates)
            it[uid] = UUID.randomUUID().toString()
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToHabit)
    }

    override suspend fun updateHabit(habit: Habit): Boolean = dbQuery {
        HabitTable.update({ HabitTable.uid eq habit.uid.uid.toString() }) {
            it[title] = habit.title
            it[description] = habit.description
            it[priority] = habit.priority
            it[type] = habit.type
            it[frequency] = habit.frequency
            it[count] = habit.count
            it[color] = habit.color
            it[date] = habit.date
            it[done_dates] = HabitMapper.toDoneDates(habit.done_dates)
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
        done_dates = HabitMapper.fromDoneDates(row[HabitTable.done_dates]),
        uid = HabitUUID(UUID.fromString(row[HabitTable.uid])),
    )

    companion object {
        fun getHabitDao(): HabitDao {
            return HabitDaoImpl()
        }
    }
}