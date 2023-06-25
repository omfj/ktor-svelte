package no.uib.echo.plugins

import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.server.plugins.defaultheaders.DefaultHeaders
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.plugins.cachingheaders.CachingHeaders
import io.ktor.server.application.Application
import io.ktor.server.application.install

fun Application.configureHTTP() {
    install(DefaultHeaders) {
        header("Server", "orca")
    }
    install(CORS) {
        anyHost() // TODO: Change this in production
        allowCredentials = true

        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Delete)

        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.Authorization)
        allowHeader(HttpHeaders.AccessControlAllowOrigin)
    }
    install(CachingHeaders)
}
