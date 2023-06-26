package no.uib.echo.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.plugins.CannotTransformContentToTypeException
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import io.ktor.server.sessions.clear
import io.ktor.server.sessions.sessions
import java.util.*
import kotlinx.serialization.Serializable
import no.uib.echo.CryptoService
import no.uib.echo.plugins.UserSession
import no.uib.echo.schema.Users
import no.uib.echo.schema.Sessions as DBSessions
import no.uib.echo.utils.UUIDSerializer

fun Application.sessionRoutes() {
    routing {
        login()
        logout()
        register()
        getUserBySession()
    }
}

@Serializable
data class LoginRequest(
    val username: String,
    val password: String
)

fun Route.login() {
    post("/login") {
        val (username, password) = try {
            call.receive<LoginRequest>()
        } catch (e: CannotTransformContentToTypeException) {
            call.respond(HttpStatusCode.BadRequest, "Invalid request")
            return@post
        }

        val user = Users.validateUser(username, password)

        if (user == null) {
            call.respond(HttpStatusCode.BadRequest, "Invalid username or password")
            return@post
        }

        val session = DBSessions.create(user.id)

        // TODO: Fix so we don't have to convert to string
        call.respond(HttpStatusCode.OK, session.id.toString())
    }
}

@Serializable
data class RegisterRequest(val username: String, val email: String, val password: String)

fun Route.register() {
    post("/register") {
        val (username, email, password) = try {
            call.receive<RegisterRequest>()
        } catch (e: CannotTransformContentToTypeException) {
            call.respond(HttpStatusCode.BadRequest, "Invalid request")
            return@post
        }

        val hashedPassword = CryptoService.hashPassword(password)

        if (Users.userExists(username, email)) {
            call.respond(HttpStatusCode.BadRequest, "Username already exists")
            return@post
        }

        val user = Users.create(
                username = username,
                email = email,
                hashedPassword = hashedPassword,
            )

        val session = DBSessions.create(user.id)

        // TODO: Fix so we don't have to convert to string
        call.respond(HttpStatusCode.OK, session.id.toString())
    }
}

fun Route.logout() {
    post("/logout") {
        call.sessions.clear<UserSession>()
        call.respond(HttpStatusCode.OK, "Successful logout")
    }
}

@Serializable
data class SessionRequest(
    @Serializable(with = UUIDSerializer::class)
    val session: UUID,
)

fun Route.getUserBySession() {
    post("/session") {
        val (session) = try {
            call.receive<SessionRequest>()
        } catch (e: CannotTransformContentToTypeException) {
            call.respond(HttpStatusCode.BadRequest, "Invalid request")
            return@post
        }

        val user = Users.getBySession(session)

        if (user == null) {
            call.respond(HttpStatusCode.BadRequest, "Invalid session")
            return@post
        }

        call.respond(HttpStatusCode.OK, user)
    }
}

/*
 * MAYBE TODO:
 * Might need an endpoint for extending the session,
 * or you could increase the expiry in the middleware
 * each time a request is made.
 */
