package com.example.listify.authentication.model

import java.util.UUID

data class User(
    val id: UUID,
    val name: String,
    val email: String,
    val accessibleLists: List<UUID> = emptyList()
)

data class CreateUserRequest(
    val name: String,
    val email: String,
    val password: String
)

data class LoginRequest(
    val email: String,
    val password: String
)