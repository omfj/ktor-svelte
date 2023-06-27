package no.uib.echo.schema

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.and

object EventRestrictions : Table("event_restrictions") {
    val id: Column<Int> = integer("id").autoIncrement()
    val max_year: Column<Int> = integer("max_year").check { it greaterEq 0 and (it lessEq 5) }
    val min_year: Column<Int> = integer("min_year").check { it greaterEq 0 and (it lessEq 5) }
    val max_spots: Column<Int?> = integer("max_spots").nullable()

    override val primaryKey = PrimaryKey(id)
}