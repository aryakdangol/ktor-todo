package com.example.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.auth.jwt.*
import io.ktor.server.auth.Principal

class JwtConfig(secret: String) {
    //jwtconfig

      val jwtIssuer = "com.example"
     val jwtRealm = "com.example.todolist"

    //claims - are the information which we store inside jwt. they are encrypted with the secret
     val CLAIM_USERID = "userId"
     val CLAIM_USERNAME = "userName"

     val jwtAlgorithm = Algorithm.HMAC512(secret)
     val jwtVerifier : JWTVerifier = JWT
        .require(jwtAlgorithm)
        .withIssuer(jwtIssuer)
        .build()

    fun generateToken(user: JwtUser): String = JWT.create()
        .withSubject("Authentication")
        .withIssuer(jwtIssuer)
        .withClaim(CLAIM_USERID, user.userId)
        .withClaim(CLAIM_USERNAME, user.userName)
        .sign(jwtAlgorithm)

    fun configureKtorFeature(config: JWTAuthenticationProvider.Config)  = with(config){
        verifier(jwtVerifier)
        realm = jwtRealm
        validate{
            val userId = it.payload.getClaim(CLAIM_USERID).asInt()
            val userName = it.payload.getClaim(CLAIM_USERNAME).asString()
            if(userId != null && userName != null){
                JwtUser(userId, userName)
            }
            else{
                null
            }
        }
    }

    data class JwtUser(val userId: Int, val userName: String): Principal

}

