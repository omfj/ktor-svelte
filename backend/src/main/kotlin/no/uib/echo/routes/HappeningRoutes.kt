package no.uib.echo.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import no.uib.echo.DatabaseFactory.dbQuery
import no.uib.echo.schema.Happening
import no.uib.echo.schema.Happenings
import org.jetbrains.exposed.sql.selectAll

fun Application.happeningRoutes() {
    routing {
        getHappening()
        createHappening()
    }
}

fun Route.getHappening() {
    get("/happening") {
        val happenings = Happenings.getAll()

        call.respond(HttpStatusCode.OK, happenings)
    }
}

fun Route.createHappening() {
    post("/happening") {
        val size = Happenings.getAll().size + 1

        Happenings.create("Happening $size")

        call.respond(HttpStatusCode.Created)
    }
}
