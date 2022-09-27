package controllers

import com.example.auth.JwtConfig
import com.example.models.LoginBody
import com.example.repository.InMemoryUserRepositoryImpl
import com.example.repository.UserRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.inMemoryloginController(jwtConfig: JwtConfig) {
    route("/api/v2/login"){
        val userRepository: UserRepository = InMemoryUserRepositoryImpl()
        post{
            val body = call.receive<LoginBody>()
            val user = userRepository.getUser(body.username, body.password)
            if (user == null){
                call.respond(HttpStatusCode.Unauthorized, "Invalid Credentials")
            }
            else{
                val token = jwtConfig.generateToken(JwtConfig.JwtUser(user.userId, user.userName))
                call.respond(HttpStatusCode.OK, token)
            }
        }
    }
}