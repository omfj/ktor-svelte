package no.uib.echo.plugins

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.session
import io.ktor.server.response.respond
import no.uib.echo.schema.isValidSession

fun Application.configureAuthentication() {
    install(Authentication) {
        session<UserSession>("session-auth") {
            validate { session: UserSession ->
                if (isValidSession(session)) {
                    session
                } else {
                    null
                }
            }
            challenge {
                call.respond(HttpStatusCode.Unauthorized)
            }
        }
    }
}
