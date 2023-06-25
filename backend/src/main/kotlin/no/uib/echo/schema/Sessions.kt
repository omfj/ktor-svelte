package no.uib.echo.schema

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import no.uib.echo.plugins.UserSession
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.select

object Sessions : Table() {
    val id = uuid("id").autoGenerate()
    val userId = uuid("user_id") references Users.id
    val expires = datetime("expires")
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)
    val updatedAt = datetime("updated_at").defaultExpression(CurrentDateTime)

    override val primaryKey = PrimaryKey(id)
}

suspend fun isValidSession(userSession: UserSession): Boolean = withContext(Dispatchers.IO) {
    return@withContext try {
        // Check if session id exists in database
        val session = Sessions.select {
            Sessions.id.eq(userSession.id)
        }.singleOrNull()

        // Check if the session exists and it's not expired
        session?.let {
            !it[Sessions.expires].isBefore(LocalDateTime.now())
        } ?: false
    } catch (e: Exception) {
        false
    }
}

