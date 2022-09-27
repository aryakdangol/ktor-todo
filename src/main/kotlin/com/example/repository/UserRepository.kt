package com.example.repository

import com.example.models.User

interface UserRepository {

    fun getUser(username: String, password: String): User?

}