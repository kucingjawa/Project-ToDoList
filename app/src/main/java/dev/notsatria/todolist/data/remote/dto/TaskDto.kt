package dev.notsatria.todolist.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class TaskDto(
    val id: Int? = null,
    val title: String,
    val description: String,
    val isCompleted: Boolean,
    val priorityId: Int,
    val createdAt: Long
)
