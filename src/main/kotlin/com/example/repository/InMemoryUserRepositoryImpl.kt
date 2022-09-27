package com.example.repository

import com.example.models.User

class InMemoryUserRepositoryImpl : UserRepository {

    private val credentialsToUser = mapOf<String, User>(
        "admin:admin" to User(1, "admin"),
        "aryak:gigachad" to User(2, "Aryak")
    )

    override fun getUser(username: String, password: String): User? {
        return credentialsToUser["$username:$password"]
    }
}