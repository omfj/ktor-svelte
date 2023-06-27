package no.uib.echo.schema

import org.jetbrains.exposed.sql.Table

object Groups : Table("groups") {
    val id = uuid("id").autoGenerate()
    val name = varchar("name", length = 50)

    override val primaryKey = PrimaryKey(id)
}
