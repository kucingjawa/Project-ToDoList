package dev.notsatria.todolist.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import dev.notsatria.todolist.data.local.entity.PriorityEntity
import dev.notsatria.todolist.data.local.entity.TaskEntity
import dev.notsatria.todolist.model.Task

data class TaskAndPriority(
    @Embedded val task: TaskEntity,
    @Relation(
        parentColumn = "priorityId",
        entityColumn = "id"
    )
    val priority: PriorityEntity
) {
    fun toTask() = Task(
        id = task.id,
        title = task.title,
        description = task.description,
        isCompleted = task.isCompleted,
        priorityId = task.priorityId,
        createdAt = task.createdAt,
        priority = priority.toPriority()
    )
}