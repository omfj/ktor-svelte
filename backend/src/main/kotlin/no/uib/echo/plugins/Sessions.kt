package no.uib.echo.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.install
import io.ktor.server.config.ApplicationConfig
import io.ktor.server.sessions.Sessions
import no.uib.echo.schema.Sessions as SessionsTable
import io.ktor.http.Cookie
import io.ktor.server.sessions.cookie
import java.util.UUID
import io.ktor.server.auth.Principal
import io.ktor.server.sessions.sessions
import io.ktor.server.sessions.set
import java.time.Duration
import java.time.LocalDateTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import no.uib.echo.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.insert

data class UserSession(val id: UUID) : Principal

fun Application.configureSessions(config: ApplicationConfig) {
    install(Sessions) {
        cookie<UserSession>("USER_SESSION") {
            cookie.extensions["SameSite"] = "lax"
            cookie.secure = config.propertyOrNull("ktor.development")?.getString().toBoolean() != true
        }
    }
}

suspend fun createSession(call: ApplicationCall, userId: UUID) = dbQuery {
    val sessionId = UUID.randomUUID()

    SessionsTable.insert {
        it[id] = sessionId
        it[this.userId] = userId
        it[expires] = LocalDateTime.now().plusDays(5)
    }

    call.sessions.set(UserSession(sessionId))
}

