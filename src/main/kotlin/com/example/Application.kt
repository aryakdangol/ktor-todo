package com.example

import com.example.auth.JwtConfig
import io.ktor.server.application.*
import com.example.plugins.*
import io.ktor.serialization.gson.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)


@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    val jwtConfig = JwtConfig(environment.config.property("jwt.secret").toString())
    install(CallLogging)
    install(ContentNegotiation){
        gson {
            setPrettyPrinting()
        }
    }
    install(Authentication){
        jwt{
            jwtConfig.configureKtorFeature(this)
        }
    }
    configureRouting(jwtConfig)
}
