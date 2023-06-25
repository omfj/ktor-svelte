package no.uib.echo.schema

import java.util.UUID
import kotlinx.serialization.Serializable
import no.uib.echo.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

@Serializable
data class Happening(val uuid: String, val name: String)

object Happenings : Table() {
    val uuid: Column<UUID> = uuid("uuid")
    val name: Column<String> = varchar("title", length = 50)

    override val primaryKey = PrimaryKey(uuid)

    suspend fun create(name: String) = dbQuery {
        Happenings.insert {
            it[uuid] = UUID.randomUUID()
            it[this.name] = name
        }
    }

    suspend fun getAll(): List<Happening> = dbQuery {
        Happenings.selectAll().map { Happening(it[uuid].toString(), it[name]) }
    }
}
