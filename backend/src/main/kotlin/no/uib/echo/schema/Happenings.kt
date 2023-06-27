package no.uib.echo.schema

import java.util.UUID
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import no.uib.echo.DatabaseFactory.dbQuery
import no.uib.echo.utils.UUIDSerializer
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

@Serializable
data class Happening(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val title: String,
    val date: LocalDateTime,
)

object Happenings : Table("happenings") {
    val id: Column<UUID> = uuid("id").autoGenerate()
    val title: Column<String> = varchar("title", length = 50)
    val date: Column<LocalDateTime> = datetime("date").defaultExpression(CurrentDateTime)
    val registerStart: Column<LocalDateTime> = datetime("register_start").defaultExpression(CurrentDateTime)
    val registerEnd: Column<LocalDateTime> = datetime("register_end").defaultExpression(CurrentDateTime)
    val event_restrictions: Column<Int> = integer("event_restrictions") references EventRestrictions.id


    override val primaryKey = PrimaryKey(id)

    suspend fun getByUuid(id: UUID): Happening? = dbQuery {
        this.select { Happenings.id eq id }.firstOrNull()?.let { toHappening(it) }
    }

    suspend fun create(title: String) = dbQuery {
        Happenings.insert {
            it[id] = UUID.randomUUID()
            it[this.title] = title
        }
    }

    suspend fun getAll(): List<Happening> = dbQuery {
        Happenings.selectAll().map { toHappening(it) }
    }

    private fun toHappening(row: ResultRow): Happening =
        Happening(
            id = row[id],
            title = row[title],
            date = row[date],
        )
}
