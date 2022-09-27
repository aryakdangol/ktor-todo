package com.example.plugins

import com.example.auth.JwtConfig
import controllers.inMemoryController
import controllers.inMemoryloginController
import controllers.mySQLController
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*

fun Application.configureRouting(jwtConfig: JwtConfig) {

    // Starting point for a Ktor app:
    routing {
        inMemoryloginController(jwtConfig)
        inMemoryController()
        authenticate {
            get("/user"){
                val user = call.authentication.principal as JwtConfig.JwtUser
                call.respond(HttpStatusCode.OK, user)
            }
            mySQLController()
        }
        get("/") {
            call.respondText("Hello World!")
        }
    }

}
