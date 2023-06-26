package no.uib.echo

import io.ktor.server.application.Application
import io.ktor.server.netty.EngineMain
import no.uib.echo.plugins.configureAuthentication
import no.uib.echo.plugins.configureHTTP
import no.uib.echo.plugins.configureRouting
import no.uib.echo.plugins.configureSerialization
import no.uib.echo.plugins.configureSessions
import no.uib.echo.routes.happeningRoutes
import no.uib.echo.routes.sessionRoutes
import no.uib.echo.routes.userRoutes

fun main(args: Array<String>) = EngineMain.main(args)

fun Application.module() {
    DatabaseFactory.init(environment.config)

    // Plugins
    configureSerialization()
    configureHTTP()
    configureRouting()
    configureSessions(environment.config)
    configureAuthentication()

    // Routes
    happeningRoutes()
    sessionRoutes()
    userRoutes()


    PostSetup()
}
