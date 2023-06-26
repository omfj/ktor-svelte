package no.uib.echo.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import java.util.*
import no.uib.echo.DatabaseFactory.dbQuery
import no.uib.echo.schema.Happening
import no.uib.echo.schema.Happenings
import org.jetbrains.exposed.sql.selectAll

fun Application.happeningRoutes() {
    routing {
        getHappening()
        getHappeningByUuid()
        createHappening()
    }
}

fun Route.getHappening() {
    get("/happening") {
        val happenings = Happenings.getAll()

        call.respond(HttpStatusCode.OK, happenings)
    }
}

fun Route.getHappeningByUuid() {
    get("/happening/{uuid}") {
        val uuid = call.parameters["uuid"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Missing uuid")

        val happening = Happenings.getByUuid(UUID.fromString(uuid))

        if (happening == null) {
            call.respond(HttpStatusCode.NotFound, "Happening not found")
            return@get
        }

        call.respond(HttpStatusCode.OK, happening)
    }
}

fun Route.createHappening() {
    post("/happening") {
        val size = Happenings.getAll().size + 1

        Happenings.create("Happening $size")

        call.respond(HttpStatusCode.Created)
    }
}
