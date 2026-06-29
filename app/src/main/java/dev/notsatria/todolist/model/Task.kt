package dev.notsatria.todolist.model

import dev.notsatria.todolist.data.local.entity.TaskEntity

data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val isCompleted: Boolean,
    val priorityId: Int,
    val createdAt: Long,
    val priority: Priority
) {
    fun toTaskEntity() = TaskEntity(
        id = id,
        title = title,
        description = description,
        isCompleted = isCompleted,
        priorityId = priorityId,
        createdAt = createdAt
    )
}