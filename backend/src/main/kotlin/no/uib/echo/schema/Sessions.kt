package no.uib.echo.schema

import java.util.*
import org.jetbrains.exposed.sql.Table
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.serialization.Serializable
import no.uib.echo.DatabaseFactory.dbQuery
import no.uib.echo.plugins.UserSession
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import no.uib.echo.utils.UUIDSerializer
import no.uib.echo.utils.getSystemTZ
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

@Serializable
data class Session(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    @Serializable(with = UUIDSerializer::class)
    val userId: UUID,
    val expires: LocalDateTime
)

object Sessions : Table("sessions") {
    val id = uuid("id").autoGenerate()
    val userId = uuid("user_id") references Users.id
    val expires = datetime("expires")
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)
    val updatedAt = datetime("updated_at").defaultExpression(CurrentDateTime)

    override val primaryKey = PrimaryKey(id)

    suspend fun create(userId: UUID): Session = dbQuery {
        val expires = Clock.System.now().plus(7, DateTimeUnit.DAY, getSystemTZ()).toLocalDateTime(getSystemTZ())

        val id = this.insert {
            it[this.userId] = userId
            it[this.expires] = expires
        } get Sessions.id

        return@dbQuery Session(id, userId, expires)
    }
}

suspend fun isValidSession(userSession: UserSession): Boolean = withContext(Dispatchers.IO) {
    return@withContext try {
        // Check if session id exists in database
        val session = Sessions.select {
            Sessions.id.eq(userSession.id)
        }.singleOrNull()

        // Check if the session exists and it's not expired
        session?.let {
            it[Sessions.expires] >= Clock.System.now().toLocalDateTime(getSystemTZ())
        } ?: false
    } catch (e: Exception) {
        false
    }
}

