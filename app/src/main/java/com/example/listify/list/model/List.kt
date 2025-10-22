package com.example.listify.list.model

import java.util.UUID

data class TaskList(
    val id: UUID,
    val name: String,
    val ownerId: UUID,
    val tasks: List<Task> = emptyList()
)

data class Task(
    val id: UUID,
    val name: String,
    val completed: Boolean = false
)

data class CreateListRequest(
    val name: String,
    val ownerId: UUID
)

data class CreateTaskRequest(
    val name: String
)