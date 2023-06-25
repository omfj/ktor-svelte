package no.uib.echo.plugins

import io.ktor.server.resources.Resources
import io.ktor.server.plugins.autohead.*
import io.ktor.server.application.*

fun Application.configureRouting() {
    install(Resources)
    install(AutoHeadResponse)
}
