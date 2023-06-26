package no.uib.echo

import io.ktor.server.config.ApplicationConfig

class PostSetup {
    init {
        printLogo()
    }

    private fun printLogo() {
        println(
            """
                   _     _                   
                  | |   | |                  
     __      _____| |__ | | _____  _ __ ___  
     \ \ /\ / / _ \ '_ \| |/ / _ \| '_ ` _ \ 
      \ V  V /  __/ |_) |   < (_) | | | | | |
       \_/\_/ \___|_.__/|_|\_\___/|_| |_| |_|
    """.trimIndent()
        )
    }
}