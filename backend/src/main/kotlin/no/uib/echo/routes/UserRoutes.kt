package no.uib.echo.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import io.ktor.server.sessions.get
import io.ktor.server.sessions.sessions
import java.util.UUID
import no.uib.echo.DatabaseFactory.dbQuery
import no.uib.echo.plugins.UserSession
import no.uib.echo.schema.Happening
import no.uib.echo.schema.Happenings
import no.uib.echo.schema.UserProfile
import no.uib.echo.schema.Users
import org.jetbrains.exposed.sql.selectAll

fun Application.userRoutes() {
    routing {
        getAllUsers()
        getUser()
        getSelf()
    }
}

fun Route.getAllUsers() {
    get("/users") {
        val users = Happenings.getAll()

        call.respond(HttpStatusCode.OK, users)
    }
}

fun Route.getUser() {
    get("/users/{id}") {
        val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Missing id")

        val user = dbQuery {
            Users.getById(UUID.fromString(id))
        }

        if (user == null) {
            call.respond(HttpStatusCode.NotFound, "User not found")
            return@get
        }

        call.respond(HttpStatusCode.OK, user)
    }
}

fun Route.getSelf() {
    get("/users/me") {
        val session = call.sessions.get<UserSession>()
        if (session == null) {
            call.respond(HttpStatusCode.BadRequest, "Invalid session")
            return@get
        }

        val user = Users.getBySession(session.id)

        if (user == null) {
            call.respond(HttpStatusCode.NotFound, "User not found")
            return@get
        }

        call.respond(HttpStatusCode.OK, UserProfile(
            id = user.id.toString(),
            username = user.username,
            email = user.email,
        ))
    }
}
